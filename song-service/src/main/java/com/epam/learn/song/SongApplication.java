package com.epam.learn.song;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
public class SongApplication {

    public static void main(String[] args) {
        log.info("Starting song application...");
        SpringApplication.run(SongApplication.class, args);
    }
}