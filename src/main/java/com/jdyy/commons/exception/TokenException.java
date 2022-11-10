package com.jdyy.commons.exception;

import cn.dev33.satoken.exception.SaTokenException;
import com.jdyy.commons.util.Result;
import com.jdyy.commons.util.Status;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * token异常类，处理token拦截信息，如 token是否有效，token是否存在 等问题
 *
 * @author LvXinming
 * @since 2022/10/25
 */


public class TokenException{
    @ExceptionHandler
    public Result tokenExceptionResult(SaTokenException saTokenException){
        Result result = new Result(Status.SUCCESS,null);
        return result;
    }
}