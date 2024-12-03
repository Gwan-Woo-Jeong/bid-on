package com.test.bidon;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // "/user/images/**" URL을 "src/main/resources/static/user/images/"로 매핑
        registry.addResourceHandler("/user/images/**")
                .addResourceLocations("classpath:/static/user/images/");
    }
}