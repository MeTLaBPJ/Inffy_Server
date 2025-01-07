package org.inffy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InffyApplication {
    public static void main(String[] args) {
        SpringApplication.run(InffyApplication.class, args);
    }
}
