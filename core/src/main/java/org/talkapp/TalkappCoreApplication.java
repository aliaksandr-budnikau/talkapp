package org.talkapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.annotation.RequestScope;

/**
 * @author Budnikau Aliaksandr
 */
@SpringBootApplication
public class TalkappCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(TalkappCoreApplication.class, args);
    }

    @RequestScope
    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger("talk-app-core");
    }
}
