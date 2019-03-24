package com.vasya;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertTrue;

public class BingSearchTest {
    private WebDriver driver;
    private String url;
    private String searchPhrase;
    private int pages;

    @Before
    public void beforeMethod() {
        driver = new ChromeDriver();
        url = "https://www.bing.com";
        searchPhrase = "First machine";
        pages = 2;
        driver.get(url);
    }

    @Test
    public void testMethodBingSearchTest() {
        driver.findElement(By.cssSelector("input[name='q']")).sendKeys(searchPhrase);
        driver.findElement(By.cssSelector("input[name='q']")).sendKeys(Keys.ENTER);

        String[] words = splitStringToWords(searchPhrase);
        List<String> searchResult = getBingSearchResult(pages);
        for (String el : searchResult) {
            for (int i = 0; i < words.length; i++) {
                assertTrue("Error!!!: \n" + el.toLowerCase() + "' doesn't contain\n" + words[i].toLowerCase(), el.toLowerCase().contains(words[i].toLowerCase()));
            }
        }
    }
    @After
    public void afterMethod() {
        driver.quit();
    }

    private ArrayList<String> getBingSearchResult(int pages) {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 1; i <= pages; i++) {
            List<WebElement> elList = driver.findElements(By.cssSelector("b_caption"));
            for (WebElement el : elList) {
                result.add(el.getText());
            }
            if (i == pages) {
                return result;
            }
            driver.findElement(By.cssSelector("a[aria-label='Страница " + (i + 1) + "']")).click();
        }
        return result;
    }
    private String[] splitStringToWords(String string) {
        return string.split("\\s+");
    }
}