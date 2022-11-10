package com.jdyy.commons.util;

import lombok.Getter;

/**
 * 结果枚举类，枚举 code 和 message
 *
 * @author LvXinming
 * @since 2022/10/13
 */

@Getter
public enum Status {
    SUCCESS(200,"正常"),//通用 成功 状态码
    UNKNOWN_FAIL(500,"未知异常"),//通用 异常 状态码
    NOT_FOUND(404,"未找到");//通用 未找到 状态码

    private final int code;
    private final String message;
Status(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
