package com.kontakt.recruitmenttask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KontaktApplication {
    public static void main(String[] args) {
        SpringApplication.run(KontaktApplication.class, args);
    }
}
