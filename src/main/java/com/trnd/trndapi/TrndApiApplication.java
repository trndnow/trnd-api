package com.trnd.trndapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TrndApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrndApiApplication.class, args);
    }

}
