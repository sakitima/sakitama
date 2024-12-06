package com.example.bookstore.common.enums;

/**
 * @author chaoluo
 */

public enum ErrorCodeEnum {

    SYSTEMERROR("-2", "系统繁忙"),
    MUSTLOGIN("-1","请先登录"),
    FAILLOGIN("1","登录失败"),
    NOEXIST("2","不存在");


    ErrorCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private final String code;
    private final String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
