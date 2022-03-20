package com.gourav.manager;

import com.gourav.dao.DAO;
import com.gourav.entities.MovieDetails;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Manager {

    @Autowired
    ChromeDriver driver;

    @Autowired
    DAO daoBean;

    public List<MovieDetails> getMovieList(WebElement movieList) {
        List<MovieDetails> list = new ArrayList<>();
        movieList.findElements(By.className("ml-item")).forEach((item) -> {
            MovieDetails movieDetails = new MovieDetails();
            String quality = item.findElement(By.className("mli-quality")).getText();
            String name = item.findElement(By.tagName("h2")).getText();
            String imageUrl = item.findElement(By.tagName("img")).getAttribute("src");
            movieDetails.setMovieName(name.strip());
            movieDetails.setQuality(quality.strip());
            movieDetails.setImageUrl(imageUrl.strip());
            list.add(movieDetails);
        });
        List<String> urlList = getUrlList(movieList);
        addDetailsToMovies(list, urlList);
        return addUrlsToMovies(list, urlList);
    }

    public void addDetailsToMovies(List<MovieDetails> list, List<String> urlList) {
        WebDriverWait wait = new WebDriverWait(driver, 1000);
        final int[] i = {0};
        list.forEach((item) -> {
            driver.get(urlList.get(i[0]));
            WebElement detailsDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("mvic-desc")));
            WebElement detailsDivLeft = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("mvici-left")));
            WebElement detailsDivRight = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("mvici-right")));
            List<WebElement> rightVals = detailsDivRight.findElements(By.tagName("p"));
            item.setGenre(detailsDivLeft.findElements(By.cssSelector("p")).get(0).getText().substring(7));
            item.setDescription(detailsDiv.findElement(By.className("desc")).getText());
            item.setDuration(rightVals.get(0).getText().substring(10).strip());
            item.setReleaseYear(Integer.parseInt(rightVals.get(2).getText().substring(8).strip()));
            item.setRatings(rightVals.get(3).getText().substring(5));
            i[0]++;
        });
    }

    public List<MovieDetails> addUrlsToMovies(List<MovieDetails> list, List<String> urlList) {
        final int[] i = {0};
        list.forEach((item) -> {
            item.setStreamUrl(getStreamURL(urlList.get(i[0])));
            i[0]++;
        });
        return list;
    }

    public List<String> getUrlList(WebElement movieList) {
        List<String> urlList = new ArrayList<>();
        movieList.findElements(By.className("ml-item")).forEach((item) -> {
            urlList.add(item.findElement(By.tagName("a")).getAttribute("href") + "watching/?ep=8");
        });
        return urlList;
    }

    public String getStreamURL(String url) {
        WebDriverWait wait = new WebDriverWait(driver, 1000);
        driver.get(url);
        WebElement iframe = driver.findElement(By.tagName("iframe"));
        wait.until(ExpectedConditions.attributeToBeNotEmpty(iframe, "src"));
        return iframe.getAttribute("src");
    }

    public void scrapeManager(WebElement movieList) {

        List<MovieDetails> list = getMovieList(movieList);
        list.forEach((i) -> {
            if (daoBean.findMovie(i.getMovieName()) == null) {
                daoBean.saveData(i);
            }
        });
    }

    public List<?> getManager() {
        return daoBean.getData();
    }

}
