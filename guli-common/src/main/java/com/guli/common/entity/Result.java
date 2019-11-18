package com.guli.common.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

@Data
@ApiModel(value = "统一全局返回结果")
public class Result {
    @ApiModelProperty(value = "是否成功")
    private boolean success;
    @ApiModelProperty(value = "返回码")
    private Integer code;
    @ApiModelProperty(value = "返回消息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<>();

    //私有构造函数（方法）
    private Result() {
    }

    //操作成功
    public static Result ok() {
        Result result = new Result();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        result.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return result;
    }

    //返回未知错误
    public static Result error() {
        Result r = new Result();
        r.setSuccess(ResultCodeEnum.UNKNOWN_REASON.getSuccess());
        r.setCode(ResultCodeEnum.UNKNOWN_REASON.getCode());
        r.setMessage(ResultCodeEnum.UNKNOWN_REASON.getMessage());
        return r;
    }
//返回自定义结果
    public static Result setResult(ResultCodeEnum resultCodeEnum){
        Result r = new Result();
        r.setMessage(resultCodeEnum.getMessage());
        r.setSuccess(resultCodeEnum.getSuccess());
        r.setCode(resultCodeEnum.getCode());
        return r;
    }
    //返回一个success
    public Result success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public Result message(String message){
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

    public Result data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public Result data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
