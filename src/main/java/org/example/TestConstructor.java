package org.example;

import org.testng.annotations.Test;

import java.io.IOException;

public class TestConstructor {

    @Test
    public void testConstructor() throws IOException, InterruptedException {
        LoginPage loginPage = new LoginPage();
        loginPage.testDropdown();
    }

    @Test
    public void testConst() throws IOException, InterruptedException {
        LoginPage loginPage = new LoginPage();
        loginPage.howToHandleWebTable();
    }
}
