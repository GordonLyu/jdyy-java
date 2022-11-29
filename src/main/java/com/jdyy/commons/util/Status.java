package com.jdyy.commons.util;

import lombok.Getter;

/**
 * 结果状态枚举类，枚举 code 和 message
 *
 * <p>200 通用正常</p>
 * <p>204 正常，但内容为空</p>
 * <p>404 异常，未找到</p>
 * <p>406 异常，内容格式不支持</p>
 * <p>409 异常，已存在或冲突</p>
 * <p>500 通用异常</p>
 *
 * @author LvXinming
 * @since 2022/10/13
 */

@Getter
public enum Status {
    SUCCESS(200,"正常"),//通用 成功 状态
    ERROR(500,"异常"),//通用 异常 状态
    NOT_FOUND(404,"未找到");//通用 未找到 状态

    private final int code;
    private final String message;
    Status(int code, String message) {
        this.code = code;
        this.message = message;
    }


    //状态码
    @Getter
    public enum Code {
        SUCCESS(200),//正常
        SUCCESS_BUT_NULL(204),//正常,但内容为空
        NOT_FOUND(404),//未找到
        NOT_SUPPORT(406),//不支持
        ERROR(500);//异常

        private final int code;
        Code(int code) {
            this.code = code;
        }
    }
}
