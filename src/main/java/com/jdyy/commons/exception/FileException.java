package com.jdyy.commons.exception;

import com.jdyy.commons.util.Result;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.SizeLimitExceededException;

/**
 *
 * 文件异常类，处理文件异常
 *
 * @author LvXinming
 * @since 2022/11/20
 */

@RestControllerAdvice
public class FileException {

    //上传文件过大异常
    @ExceptionHandler(FileUploadException.class)
    public Result handlerSizeLimitExceededException(SizeLimitExceededException e){
        return new Result(500,"文件过大，请重新上传",e.getMessage());
    }
}
