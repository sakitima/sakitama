package com.example.bookstore.common.exception;

import com.example.bookstore.common.enums.ErrorCodeEnum;

/**
 * @author chaoluo
 */

public class BusinessException extends RuntimeException{

    private Integer errorCode=-1000;

    public BusinessException(String code, String message) {
        super(message);
    }
    public BusinessException(Integer errorCode, String message){
        this(ErrorCodeEnum.NOEXIST.getCode(), message);
        this.errorCode = errorCode;
    }
    public Integer getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
