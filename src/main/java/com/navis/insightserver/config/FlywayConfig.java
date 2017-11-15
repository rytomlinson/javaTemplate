package com.navis.insightserver.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by darrell-shofstall on 11/15/17.
 */
@Configuration
public class FlywayConfig {
    @Bean
    @Profile("dev")
    public Flyway flyway(DataSource dataSource) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setLocations("classpath:/db/migration/dev", "classpath:/db/data/dev");
        flyway.clean();
        flyway.migrate();

        return flyway;
    }
}
