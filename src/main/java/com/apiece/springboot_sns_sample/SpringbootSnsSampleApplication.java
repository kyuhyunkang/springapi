package com.apiece.springboot_sns_sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringbootSnsSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSnsSampleApplication.class, args);
    }

}
