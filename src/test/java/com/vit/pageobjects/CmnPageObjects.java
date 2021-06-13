package com.vit.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CmnPageObjects {
    private static final Logger logger = LogManager.getLogger(CmnPageObjects.class);
    WebDriver driver;

    private By search_text_box = By.id("twotabsearchtextbox");
    private By search_button = By.xpath("//input[@value='Go']");
    private By hamburger_menu_link = By.id("nav-hamburger-menu");
    private By nav_link_logo = By.xpath("//a[@class='nav-logo-link']");
    private By nav_link_cart = By.id("nav-cart");
    private By nav_link_prime = By.id("nav-link-prime");
    private By nav_link_orders = By.id("nav-orders");
    private By nav_link_acount = By.id("nav-link-accountList");

    private String hamburger_menu_category_link_xpath = "//div[@id='hmenu-content']//div[text()='%s']";
    private String hamburger_menu_sub_category_link_xpath = "//div[@id='hmenu-content']//a[text()='%s']";

    public CmnPageObjects(WebDriver driver) {
        this.driver = driver;
    }

    public void SetSearchTextBox(String text) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
        WebElement elementSearchBox = webDriverWait.until(ExpectedConditions.elementToBeClickable(search_text_box));
        elementSearchBox.clear();
        elementSearchBox.sendKeys(text);
        logger.info("Value entered in search box: " + text);
    }

    public void ClickOnSearchButton() {
        driver.findElement(search_button).click();
        logger.info("Clicked on Search Button");
    }


    public void validatePageTitleMatch(String expectedTitle) {
        
    }
}


