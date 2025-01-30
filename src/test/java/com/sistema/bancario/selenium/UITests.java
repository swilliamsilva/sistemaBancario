package com.sistema.bancario.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.jupiter.api.Assertions.*;

public class UITests {
    
    private WebDriver driver;

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "caminho/para/seu/chromedriver");
        driver = new ChromeDriver();
    }

    @Test
    public void testLoginPage() {
        driver.get("http://localhost:8080/login");
        driver.findElement(By.id("username")).sendKeys("usuario");
        driver.findElement(By.id("password")).sendKeys("senha");
        driver.findElement(By.id("login-button")).click();
        
        assertTrue(driver.getCurrentUrl().contains("/dashboard"));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
} 