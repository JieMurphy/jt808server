package org.yzh.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.yzh.web.component.LoginInterceptor;

@Configuration
public class UsersConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        InterceptorRegistration loginRegistry = registry.addInterceptor(loginInterceptor);

        loginRegistry.addPathPatterns("/**");

        loginRegistry.excludePathPatterns("/");
        loginRegistry.excludePathPatterns("/sign");
        loginRegistry.excludePathPatterns("/login");

        loginRegistry.excludePathPatterns("/*/login");
        loginRegistry.excludePathPatterns("/*/sign");
    }
}
