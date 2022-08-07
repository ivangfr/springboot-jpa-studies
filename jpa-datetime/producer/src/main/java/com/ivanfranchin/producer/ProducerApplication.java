package com.ivanfranchin.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.ZoneId;

@Slf4j
@SpringBootApplication
public class ProducerApplication {

    @PostConstruct
    public void init() {
        log.info("TimeZone configured: {}", ZoneId.systemDefault());
    }

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }
}
