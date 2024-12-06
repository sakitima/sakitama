package com.example.bookstore.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bookstore.common.exception.BusinessException;
import com.example.bookstore.common.mapper.UserMapper;
import com.example.bookstore.common.enums.ErrorCodeEnum;
import com.example.bookstore.common.domain.User;
import com.example.bookstore.common.request.LoginReqDto;
import com.example.bookstore.common.request.RegisterReqDto;
import com.example.bookstore.common.response.ResponseResult;
import com.example.bookstore.common.util.AuthHelper;
import com.example.bookstore.common.util.EncryptUtil;
import com.example.bookstore.common.util.RedisUtil;
import com.example.bookstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author chaoluo
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private AuthHelper authHelper;

    @Resource
    private UserMapper userMapper;

    @Override
    public ResponseResult userRegister(RegisterReqDto registerReqDto) {
        log.info("userRegister requestParam: {}", JSON.toJSONString(registerReqDto));
        try {
            Long count = userMapper.selectCount(new QueryWrapper<User>()
                    .lambda()
                    .eq(User::getPhone, registerReqDto.getPhoneNumber()));
            if (count > 0){
                return ResponseResult.error(ErrorCodeEnum.NOEXIST.getCode(),"手机号码已存在，请重新输入");
            }
            User user = new User();
            user.setName(registerReqDto.getUserName());
            user.setPhone(registerReqDto.getPhoneNumber());
            user.setUserId(Integer.parseInt(String.valueOf(RandomUtil.randomInt(6))));
            //密码加密，正常情况需要前端加密之后传给后端
            user.setPassword(EncryptUtil.md5(registerReqDto.getPassword()));
            userMapper.insert(user);
            return ResponseResult.ok("注册成功");
        }catch (RuntimeException e){
            log.error("注册失败: {},失败参数: {}",e,JSON.toJSONString(registerReqDto));
            return ResponseResult.systemError();
        }
    }

    @Override
    public ResponseResult userLogin(LoginReqDto loginReqDto) {
        log.info("userLogin requestParam: {}", JSON.toJSONString(loginReqDto));
        try {
            User user = userMapper.selectOne(new QueryWrapper<User>()
                    .lambda()
                    .eq(User::getPhone, loginReqDto.getPhoneNumber()));
            if (user == null){
                return ResponseResult.error(ErrorCodeEnum.NOEXIST.getCode(),"用户不存在");
            }
            if (!user.getPassword().equals(EncryptUtil.md5(loginReqDto.getPassword()))){
                return ResponseResult.error(ErrorCodeEnum.FAILLOGIN,"登录失败");
            }
            String token = user.getUserId()+ user.getPhone() + UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
            redisUtil.set("login:token:"+ token, JSON.toJSONString(user), 1800L);
            return ResponseResult.ok(token);
        }catch (BusinessException e){
            log.error("登录失败: {},失败参数: {}",e,JSON.toJSONString(loginReqDto));
            return ResponseResult.systemError();
        }catch (Exception e){
            log.error("登录失败: {}",e);
            return ResponseResult.systemError();
        }
    }

    @Override
    public ResponseResult getUser(HttpServletRequest httpRequest,LoginReqDto loginReqDto) {
        log.info("getUser requestParam: {}", JSON.toJSONString(loginReqDto));
        try {
             User user = authHelper.getLoginUser(httpRequest);
            if (user != null){
                return ResponseResult.ok(user);
            }
            user = userMapper.selectOne(new QueryWrapper<User>()
                    .lambda()
                    .eq(User::getPhone, loginReqDto.getPhoneNumber()));
            if (user == null){
                return ResponseResult.error(ErrorCodeEnum.NOEXIST.getCode(),"用户不存在");
            }
            return ResponseResult.ok(user);
        }catch (RuntimeException e){
            log.error("查询用户失败: {},失败参数: {}",e,JSON.toJSONString(loginReqDto));
            return ResponseResult.systemError();
        }
    }
}
