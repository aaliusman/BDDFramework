package org.example;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.*;

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


    @Test(priority = -1)
    public void validateLoginPage() throws InterruptedException, IOException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.statefarm.com");
        driver.findElement(By.xpath("//*[@id='quote-main-zip-code-input']")).sendKeys("19047");
        driver.findElement(By.xpath("//button[@data-for='insurance']")).click();
        List<WebElement> links = driver.findElements(By.xpath("//ul[@class='-oneX-subMenu-items -oneX-hidden  ']/li/a"));

        for (int i = 0; i < links.size(); i++) {
            System.out.println(links.get(i).getText());
        }
        Thread.sleep(2000);

        Properties prop = getDataFromPropertyFile("config.properties");
        System.out.println("username: " + prop.getProperty("name"));


        int lengthOfZipCode = driver.findElement(By.xpath("//*[@id='quote-main-zip-code-input']")).getAttribute("value").length();
//        Assert.assertEquals(lengthOfZipCode, 5);
        Thread.sleep(2000);

        driver.findElement(By.cssSelector("button[class='-oneX-header-top-menu-btn'] span")).click();
        String password = prop.getProperty("password");
        System.out.println(password);
        driver.findElement(By.cssSelector("#util-login-password")).sendKeys(password);
        Thread.sleep(4000);
    }

    @Test
    public void testDropdown() throws InterruptedException {
        List<String> expectedDropDown = new ArrayList<>(Arrays.asList("Select", "Option1", "Option2", "Option3"));
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");
        Select select = new Select(driver.findElement(By.id("dropdown-class-example")));
        Thread.sleep(3000);
        select.selectByIndex(3);
        Thread.sleep(2000);
        select.selectByVisibleText("Option2");
        Thread.sleep(2000);
        select.selectByValue("option1");
        List<WebElement> e = select.getOptions();
        for (int i = 0; i<e.size();i++){
            Assert.assertEquals(e.get(i).getText(), expectedDropDown.get(i));
        }

//        for (WebElement ele : e) {
//            System.out.println(ele.getText());
//        }
    }

    @Test(priority = 1)
    public void alertExample() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");
        driver.findElement(By.id("alertbtn")).click();
        driver.switchTo().alert().accept();
        Thread.sleep(500);
        driver.findElement(By.cssSelector("#confirmbtn")).click();
        driver.switchTo().alert().dismiss();
        Thread.sleep(300);
        driver.findElement(By.cssSelector("#confirmbtn")).click();
        String getAlertText = driver.switchTo().alert().getTex
        String expectedAlertText = "Hello , Are you sure you want to confirm";
        Assert.assertEquals(getAlertText, expectedAlertText);


    }

    @Test(priority = 2)
    public void switchTab() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");
        String mainWindow = driver.getWindowHandle();
        driver.findElement(By.id("opentab")).click();
        Thread.sleep(2300);
        Set<String> wins = driver.getWindowHandles();
        for (String w : wins) {
            if (!w.equalsIgnoreCase(mainWindow)) {
                driver.switchTo().window(w);
                Thread.sleep(2000);
                String courses = driver.findElement(By.linkText("Courses")).getText();
                Assert.assertEquals(courses, "Courses");
                driver.close();
            }
            driver.switchTo().window(mainWindow);
            driver.findElement(By.id("opentab")).isDisplayed();
            Thread.sleep(2000);
        }
    }

    @Test(priority = 4)
    public void howToHandleWebTable() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");
        List<WebElement> columNumber = driver.findElements(By.xpath("//table[@id='product']/tbody/tr/th"));
        int columCount = columNumber.size();
        System.out.println("No of rows in this table : " + columCount);

        List<WebElement> rowsNumber = driver.findElements(By.xpath("//table[@class='table-display']/tbody/tr/td"));
        int rowCount = rowsNumber.size();

        for (int i = 0;i<columCount;i++) {
            System.out.println("Colum Name: " + columNumber.get(i).getText());
        }
            for (int x = 0; x<rowCount; x++) {
                System.out.println("Rows value: "+ rowsNumber.get(x).getText());

        }

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~`");
        System.out.println("No of rows in this table : " + rowCount);

        System.out.println(rowsNumber.get(0).getText());
        System.out.println(rowsNumber.get(1).getText());
        System.out.println(rowsNumber.get(2).getText());


        System.out.println(columNumber.get(0).getText());
        System.out.println(columNumber.get(1).getText());
        System.out.println(columNumber.get(2).getText());
    }

    @Test(priority = 3)
    public void validateAutoSuggest() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");
        driver.findElement(By.id("autocomplete")).sendKeys("United St");
        Thread.sleep(1400);
        driver.findElement(By.xpath("//div[contains(@id,'ui-id')]")).click();
        Thread.sleep(1400);
    }


    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
