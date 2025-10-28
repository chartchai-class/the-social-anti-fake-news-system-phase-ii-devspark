package com.devspark.antifakenews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Main Spring Boot Application class for Anti-Fake News System
 * 
 * @author DevSpark Team
 * @version 1.0.0
 */
@SpringBootApplication
@EnableJpaAuditing
public class AntiFakeNewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AntiFakeNewsApplication.class, args);
    }
}
