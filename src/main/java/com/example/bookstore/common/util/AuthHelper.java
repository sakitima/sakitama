package com.example.bookstore.common.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.example.bookstore.common.domain.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class AuthHelper {

    @Resource
    private RedisUtil redisUtil;

    public User getLoginUser(HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("token");
        return getLoginUser(token);
    }

    public User getLoginUser(String token) {
        if (StrUtil.isEmpty(token)){
            return null;
        }
        String value = (String) redisUtil.get("login:token:" + token);
        if (StrUtil.isNotEmpty(value)){
            User user = JSONObject.parseObject(value, User.class);
            return user;
        }
        return null;
    }


}
