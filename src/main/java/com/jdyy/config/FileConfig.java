package com.jdyy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * 静态文件 设置类
 *
 * @author LvXinming
 * @since 2022/11/19
 */

@EnableWebMvc
@Configuration
public class FileConfig implements WebMvcConfigurer {

    private static String relativePath = FileConfig.class.getClassLoader().getResource("").getPath();

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        relativePath = URLDecoder.decode(relativePath, StandardCharsets.UTF_8);//处理字符问题
        registry.addResourceHandler("/**").addResourceLocations("file:"+relativePath+"static/");
    }
}
