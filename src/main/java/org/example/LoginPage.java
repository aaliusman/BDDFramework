package org.example;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class LoginPage {


    WebDriver driver = null;
    String expectedErrorMessage = "Incorrect format Enter a valid 5-digit US ZIP Code.";

    public Properties getDataFromPropertyFile(String fileName) throws IOException {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            fis.close();
        }
        return prop;
    }

    

    @Test
    public void validateLoginPage() throws InterruptedException, IOException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.statefarm.com");
        driver.findElement(By.xpath("//*[@id='quote-main-zip-code-input']")).sendKeys("19047");
        driver.findElement(By.xpath("//button[@data-for='insurance']")).click();
        List<WebElement> links = driver.findElements(By.xpath("//ul[@class='-oneX-subMenu-items -oneX-hidden  ']/li/a"));

        for (int i = 0;i<links.size();i++) {
                System.out.println(links.get(i).getText());
        }
        Thread.sleep(2000);

        Properties prop = getDataFromPropertyFile("config.properties");
        System.out.println("username: "+ prop.getProperty("name"));


        int lengthOfZipCode = driver.findElement(By.xpath("//*[@id='quote-main-zip-code-input']")).getAttribute("value").length();
//        Assert.assertEquals(lengthOfZipCode, 5);
        Thread.sleep(2000);
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
