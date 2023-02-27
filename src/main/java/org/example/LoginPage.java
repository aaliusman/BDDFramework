package org.example;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.List;

public class LoginPage {


   WebDriver driver = null;
    String expectedErrorMessage = "Incorrect format Enter a valid 5-digit US ZIP Code.";

    

    @Test
    public void validateLoginPage() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.statefarm.com");
        driver.findElement(By.xpath("//*[@id='quote-main-zip-code-input']")).sendKeys("19047");
        driver.findElement(By.xpath("//*[@id='oneX-header']/nav/section[3]/div[1]/div[1]/ul/li[1]/div/button")).click();
        List<WebElement> links = driver.findElements(By.xpath("//ul[@class='-oneX-subMenu-items -oneX-hidden  ']/li/a"));

        for (int i = 0;i<links.size();i++) {
                System.out.println(links.get(i).getText());
        }
        Thread.sleep(2000);

        int lengthOfZipCode = driver.findElement(By.xpath("//*[@id='quote-main-zip-code-input']")).getAttribute("value").length();
//        Assert.assertEquals(lengthOfZipCode, 5);
        Thread.sleep(2000);
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
