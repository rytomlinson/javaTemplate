package com.navis.insightserver.config;

import org.springframework.boot.actuate.autoconfigure.ManagementServerProperties;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * Created by darrell-shofstall on 9/14/17.
 */
@EnableWebSecurity
@Order(ManagementServerProperties.ACCESS_OVERRIDE_ORDER)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers("/").permitAll();
        httpSecurity.antMatcher("/actuator/**").authorizeRequests().anyRequest().authenticated();
        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();
        }
    }
