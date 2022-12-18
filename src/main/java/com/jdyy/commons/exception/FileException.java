package com.jdyy.commons.exception;

import com.jdyy.commons.util.Result;
import com.jdyy.config.FileConfig;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.naming.SizeLimitExceededException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 *
 * 文件异常类，处理文件异常
 *
 * @author LvXinming
 * @since 2022/11/20
 */

@RestControllerAdvice
public class FileException implements WebMvcConfigurer {

    private static String relativePath = FileConfig.class.getClassLoader().getResource("").getPath();

    public void addResourceHandlers(ResourceHandlerRegistry registry){

        relativePath = URLDecoder.decode(relativePath,StandardCharsets.UTF_8);//处理字符问题
        registry.addResourceHandler("/**").addResourceLocations("file:"+relativePath+"static/");
    }
}
