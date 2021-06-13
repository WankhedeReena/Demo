package com.vit.core;

import com.vit.pageobjects.*;
import io.cucumber.java.Scenario;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
 @Log4j2
public class TestContext {
    public WebDriver driver;
    public String base_url = "https://amazon.in";
    public int implicit_wait_timeout_in_sec = 20;
    public Scenario scn;

    public CmnPageObjects cmnPageObjects;
    public HomePageObjects homePageObjects;
    public SignInPageObjects signInPageObjects;
    public SearchPageObjects searchPageObjects;
    public ProductDescriptionPageObjects productDescriptionPageObjects;

    public void initializeWebDriver() throws Exception {
        //Get the browser name by default it is chrome
        System.setProperty("webdriver.chrome.driver","D:\\Selenium\\exes\\chromedriver\\chromedriver.exe");

        String browserName = WebDriverFactory.getBrowserName();
        driver = WebDriverFactory.getWebDriverForBrowser(browserName);
        log.info("Browser invoked.");
    }

    public void initializePageObjects(){
        cmnPageObjects = new CmnPageObjects(driver);
        homePageObjects = new HomePageObjects(driver);
        signInPageObjects = new SignInPageObjects(driver);
        searchPageObjects = new SearchPageObjects(driver);
        productDescriptionPageObjects = new ProductDescriptionPageObjects(driver);
    }
}

