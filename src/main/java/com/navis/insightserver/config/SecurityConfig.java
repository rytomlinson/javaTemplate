package com.navis.insightserver.config;

import org.pac4j.core.config.Config;
import org.pac4j.springframework.web.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
@ComponentScan(basePackages = {"org.pac4j.springframework.web"})
public class SecurityConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private Config config;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityInterceptor(config, "OidcClient")).addPathPatterns("/secure/**");
    }
}
