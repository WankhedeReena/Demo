package com.vit.stepdefs;

import com.vit.core.TestContext;
import com.vit.core.WebDriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Hooks {
    TestContext testContext;
    WebDriver driver;

    //Dependency Injections using Pico Container
    public Hooks(TestContext testContext) {
        this.testContext = testContext;
        driver = testContext.driver;
    }

    @Before
    public void setUp(Scenario scn) throws Exception {
        testContext.initializeWebDriver();
        testContext.initializePageObjects();
        testContext.scn = scn;
    }

    @After(order=1)
    public void cleanUp(Scenario scn){
        WebDriverFactory.quitDriver();
        scn.log("Browser Closed");
    }

    @After(order=2)
    public void takeScreenShot(Scenario scn) {
        if (scn.isFailed()) {
            TakesScreenshot screenShot = (TakesScreenshot)driver;
            byte[] data = screenShot.getScreenshotAs(OutputType.BYTES);
            scn.attach(data, "image/png","Failed Step Name: " + scn.getName());
        }else{
            scn.log("Test case is passed, no screen shot captured");
        }
    }}

