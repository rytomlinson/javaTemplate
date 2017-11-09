package com.navis.insightserver;

/**
 * Created by darrell-shofstall on 8/9/17.
 */
import org.pac4j.core.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;


@EnableCaching
@SpringBootApplication
@Import(Config.class)
public class MyApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MyApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MyApplication.class, args);
    }
}


