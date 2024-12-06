package com.example.bookstore.common.response;


import com.example.bookstore.common.enums.ErrorCodeEnum;

import java.util.HashMap;

/**
 * @author chaoluo
 */
public class ResponseResult extends HashMap<String, Object> {

    private static final long serialVersionUID = 417546153847635139L;

    private ResponseResult() {
    }

    public static ResponseResult systemError() {
        return error(ErrorCodeEnum.SYSTEMERROR);
    }

    public static ResponseResult error(ErrorCodeEnum errorCodeEnum) {
        return error("", errorCodeEnum);
    }

    public static ResponseResult error(String prefix, ErrorCodeEnum errorCodeEnum) {
        ResponseResult r = new ResponseResult();
        r.put("code", errorCodeEnum.getCode());
        r.put("msg", prefix + errorCodeEnum.getMsg());
        r.put("data", null);
        return r;
    }
    public static ResponseResult error(ErrorCodeEnum errorCodeEnum,String suffix) {
        ResponseResult r = new ResponseResult();
        r.put("code", errorCodeEnum.getCode());
        r.put("msg", errorCodeEnum.getMsg() + suffix);
        r.put("data", null);
        return r;
    }
    public static ResponseResult error(String message) {
        ResponseResult r = new ResponseResult();
        r.put("code", 9999);
        r.put("msg", message);
        r.put("data", null);
        return r;
    }

    public static ResponseResult error(String code,String message) {
        ResponseResult r = new ResponseResult();
        r.put("code", code);
        r.put("msg", message);
        r.put("data", null);
        return r;
    }

    public static ResponseResult ok() {
        return ok(null);
    }

    public static boolean isOk(ResponseResult responseResult) {
        return "0".equals(responseResult.get("code").toString());
    }

    public static ResponseResult ok(Object data) {
        ResponseResult r = new ResponseResult();
        r.put("code", 0);
        r.put("data", data);
        r.put("msg", null);
        return r;
    }

    public static ResponseResult ok(String  msg,Integer code) {
        ResponseResult r = new ResponseResult();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    @Override
    public ResponseResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public String getCode(){
        return get("code").toString();
    }

    public int getIntCode(){
        return Integer.parseInt(get("code").toString());
    }

    public String getMsg(){
        return get("msg").toString();
    }

    public Object getData(){
        return get("data");
    }


}
