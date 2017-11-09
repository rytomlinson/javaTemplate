package com.navis.insightserver.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

/**
 * Created by darrell-shofstall on 8/11/17.
 */
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
