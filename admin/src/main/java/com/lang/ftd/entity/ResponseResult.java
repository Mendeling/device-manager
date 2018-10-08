package com.lang.ftd.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseResult<T> implements Serializable {
    private boolean success = true;
    private String errMsg = "";
    private String errCode = "";
    private T data;

    private  ResponseResult(){}
    public static ResponseResult success() {
        return new ResponseResult();
    }

    public static ResponseResult error(String errCode,String errMsg,String flowNo) {
        ResponseResult result = new ResponseResult();
        result.setErrCode(errCode);
        result.setErrMsg(errMsg);
        result.setSuccess(false);
        return result;
    }
    public static ResponseResult error(String errorCode ,String errorMsg) {
        ResponseResult result = new ResponseResult();
        result.setErrCode(errorCode);
        result.setErrMsg(errorMsg);
        result.setSuccess(false);
        return result;
    }

    public static ResponseResult success(Object data) {
        ResponseResult<Object> result = new ResponseResult<Object>();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

}
