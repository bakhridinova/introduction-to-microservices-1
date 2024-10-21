package com.epam.learn.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ResourceApplication {

    public static void main(String[] args) {
        log.info("Starting resource application...");
        SpringApplication.run(ResourceApplication.class, args);
    }
}