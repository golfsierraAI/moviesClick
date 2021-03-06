package com.gourav.scheduler;

import com.gourav.manager.Manager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Scheduler {
    @Autowired
    private ChromeDriver driver;

    @Autowired
    private Manager manager;

//    @Scheduled(cron = "0 30 10 * * ?", zone = "GMT+5:30")
//    @PostConstruct
    private void scrape() {
        String url = "http://ww1.gomoviesfree.org/movies/15";
        WebDriverWait wait = new WebDriverWait(driver, 1000);
        System.out.println("Hello");
        driver.get(url);
        WebElement movieList=wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("movies-list-full")));
        manager.scrapeManager(movieList);
    }

    @PreDestroy
    public void destroy() {
        driver.close();
        driver.quit();
    }
}
