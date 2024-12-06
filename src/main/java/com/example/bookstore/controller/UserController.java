package com.example.bookstore.controller;


import com.example.bookstore.common.request.LoginReqDto;
import com.example.bookstore.common.request.RegisterReqDto;
import com.example.bookstore.common.response.ResponseResult;
import com.example.bookstore.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;

/**
 * @author chaoluo
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseResult userRegister(@RequestBody @Valid RegisterReqDto registerReqDto){
       return userService.userRegister(registerReqDto);
    }

    @PostMapping("/login")
    public ResponseResult userLogin(@RequestBody @Valid LoginReqDto loginReqDto){
        return userService.userLogin(loginReqDto);
    }

    @PostMapping ("/getUser")
    public ResponseResult getUser(HttpServletRequest httpRequest, @RequestBody LoginReqDto loginReqDto){
        return userService.getUser(httpRequest,loginReqDto);
    }
}
