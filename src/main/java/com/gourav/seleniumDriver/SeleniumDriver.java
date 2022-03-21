package com.gourav.seleniumDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

@Configuration
public class SeleniumDriver {

    @PostConstruct
    void postConstructor(){
        WebDriverManager.chromedriver().setup();
    }

    @Bean
    public ChromeDriver driver(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-dev-shm-usage");
        return new ChromeDriver(options);
    }
}