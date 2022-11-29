package com.jdyy.commons.util;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 结果类
 *
 * @author LvXinming
 * @since 2022/10/13
 */

@Data
    public class Result implements Serializable {
    @Schema(name = "状态码")
    private int code;//状态码

     @Schema(name = "状态信息")
    private String message;//状态信息

     @Schema(name = "返回数据")
    private Object data;//数据

    public Result(){}
    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public Result(Status status) {
        this.code = status.getCode();
        this.message = status.getMessage();
    }
    public Result(Status status, Object data) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.data = data;
    }


    //成功
    public static Result success(Object data){
        return new Result(Status.SUCCESS,data);
    }
    public static Result success(String message,Object data){
        return new Result(200,message,data);
    }
    public static Result success(int code,String message,Object data){
        return new Result(code,message,data);
    }


    //失败
    public static Result fail(Object data){
        return new Result(Status.ERROR,data);
    }
    public static Result fail(String message,Object data){
        return new Result(500,message,data);
    }
    public static Result fail(int code,String message){
        return new Result(code,message);
    }
    public static Result fail(int code,String message,Object data){
        return new Result(code,message,data);
    }
    public static Result fail(String message){
        return new Result(500,message,null);
    }
}
