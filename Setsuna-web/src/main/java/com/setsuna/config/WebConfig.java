package com.setsuna.config;

import com.setsuna.constants.Constants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //本地静态资源映射
        registry
                .addResourceHandler(Constants.RESOURCE_PREFIX + "/**")
                .addResourceLocations("data:" + SetsunaConfig.getFilePath() + "/");
    }

}
