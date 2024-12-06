package com.example.bookstore.service;

import com.example.bookstore.common.request.LoginReqDto;
import com.example.bookstore.common.request.RegisterReqDto;
import com.example.bookstore.common.response.ResponseResult;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author chaoluo
 */
public interface UserService {

    ResponseResult userRegister(RegisterReqDto registerReqDto);

    ResponseResult userLogin(LoginReqDto loginReqDto);

    ResponseResult getUser(HttpServletRequest httpRequest,LoginReqDto loginReqDto);
}
