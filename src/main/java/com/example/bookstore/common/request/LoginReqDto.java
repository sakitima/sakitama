package com.example.bookstore.common.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author chaoluo
 */
@Data
public class LoginReqDto {

    @NotNull(message = "密码不能为空")
    private String password;

    @NotNull(message = "用户名不能为空")
    @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$",message = "手机号码格式错误")
    private String phoneNumber;

}
