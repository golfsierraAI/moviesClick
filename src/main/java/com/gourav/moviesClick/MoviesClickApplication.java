package com.gourav.moviesClick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = "com.gourav")
@EntityScan( basePackages = "com.gourav")
public class MoviesClickApplication {
    public static void main(String[] args) {
        SpringApplication.run(MoviesClickApplication.class, args);
    }
}
