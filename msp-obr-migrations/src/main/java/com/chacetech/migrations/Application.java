package com.chacetech.migrations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude ={AopAutoConfiguration.class, WebMvcAutoConfiguration.class})
@ComponentScan(basePackages = {"com.chacetech"})
@EnableMongoRepositories(basePackages = { "com.chactech.serviceproviders.common.dao"})
public class Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        try {
            LOGGER.info("start_migrations - mongo");
            ConfigurableApplicationContext app = SpringApplication.run(Application.class);

            LOGGER.info("migrations completed successfully");
            LOGGER.info("System.exit()");
            int rc = SpringApplication.exit(app, () -> 0);
            System.exit(rc);
        } catch (Exception ex) {
            LOGGER.error("migrations failed", ex);
            System.exit(1);
        }
    }


}
