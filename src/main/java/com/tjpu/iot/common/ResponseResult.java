package com.tjpu.iot.common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

@JsonSerialize
public class ResponseResult implements Serializable {
    /** 返回的数据 */
    private Object data;

    /** 成功or失败 */
    private boolean success;

    /** 消息 */
    private String message;

    /** 状态码 */
    private Integer code;

    /** 前端弹窗级别："warning", "error", "success", "info" */
    private String icon;

    public ResponseResult() {

    }

    public ResponseResult(boolean success, String message) {
        this.success = success;
        this.message = message;
        if (success) icon = "success";
        else icon = "error";
    }

    public ResponseResult(boolean success, String message, Integer code) {
        this(success, message);
        this.code = code;
    }

    public ResponseResult(Object data, boolean success, String message, Integer code) {
        this(success, message, code);
        this.data = data;
    }

    public static ResponseResult responseResult(Object data, boolean success, String message, Integer code,String icon) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.data = data;
        responseResult.success = success;
        responseResult.message = message;
        responseResult.code = code;
        responseResult.icon = icon;
        return responseResult;
    }

    // 返回成功结果
    public static ResponseResult resultSuccess(Object data, String message) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.data = data;
        responseResult.success = true;
        responseResult.message = message;
        return responseResult;
    }

    // 返回失败结果
    public static ResponseResult resultError(String message) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.success = false;
        responseResult.message = message;
        return responseResult;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "data=" + data +
                ", success=" + success +
                ", message='" + message + '\'' +
                ", code=" + code +
                ", icon='" + icon + '\'' +
                '}';
    }
}
