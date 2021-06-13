package com.vit.stepdefs;


import com.vit.core.TestContext;
import com.vit.core.WebDriverFactory;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;

public class StepDefs1 {
    TestContext testContext;
    public Scenario scn;

    //Dependency Injections using Pico Container
    public StepDefs1(TestContext testContext){
        this.testContext = testContext;
        this.scn = testContext.scn;
    }

    @Given("User navigated to the home application url")
    public void user_navigated_to_the_home_application_url() {
        System.setProperty("webdriver.chrome.driver","D:\\Selenium\\exes\\chromedriver\\chromedriver.exe");
        WebDriverFactory.navigateToTheUrl(testContext.base_url);
        scn.log("Browser navigated to URL: " + testContext.base_url);
        String expected = "Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in";
        testContext.cmnPageObjects.validatePageTitleMatch(expected);
        
    }

    @When("User Search for product {string}")
    public void user_search_for_product(String productName) {
        testContext.cmnPageObjects.SetSearchTextBox(productName);
        testContext.cmnPageObjects.ClickOnSearchButton();
        scn.log("Product Searched: " + productName);
    }

    @Then("Search Result page is displayed")
    public void search_result_page_is_displayed() {
        testContext.searchPageObjects.ValidateProductSearchIsSuccessfull();
    }


    @When("User click on any product")
    public void userClickOnAnyProduct() {

      testContext.searchPageObjects.ClickOnTheProductLink(0);
    }

    @Then("Product Description is displayed in new tab")
    public void productDescriptionIsDisplayedInNewTab() {
        WebDriverFactory.switchBrowserToTab();
        scn.log("Switched to the new window/tab");
        testContext.productDescriptionPageObjects.ValidateProductTileIsCorrectlyDisplayed();
        testContext.productDescriptionPageObjects.ValidateAddToCartButtonIsCorrectlyDisplayed();
        testContext.productDescriptionPageObjects.clickOnAddToCartButton();
        testContext.productDescriptionPageObjects.checkAddedToCartMessageIsDisplayed();
        
    }

    @Then("User cart is updated with the products and quantity")
    public void user_cart_is_updated_with_the_products_and_quantity() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
        //This part is pending
        //Click on the cart button on top right
        //Check all the products with the right products and prices and quantity are added
        //Total is correct
    }

    //NOTE: In order to simplify explanation, I am not using Page Object Model in this method.
    //I am directly using xpaths in the Step Defs. This is not right and these xpaths have to
    //to be moved to respective page object model file.
    @When("User add the products with defined price range and quantity listed below")
    public void user_add_the_products_with_defined_price_range_and_quantity_listed_below(List<Map<String,String>> data) {
        for (int i=0; i<=data.size()-1;i++){
            searchAndAddProducts(data,i);
            scn.log("First Product added and searched. " + data.get(i).toString());
        }
    }

    // Common Method to Iterated
    public void searchAndAddProducts(List<Map<String,String>> data, int index){
        String product_name = data.get(index).get("ITEM");
        int product_price_limit = Integer.parseInt(data.get(index).get("PRICE_LESS_THAN"));
        String product_quantity = data.get(index).get("QUANTITY");

        //Reusing Existing methods.
        //You can use existing Step Defs methods.
        //No issues there.
        user_search_for_product(product_name);

        //Get the List of Products.
        //This XPATH will get all the product links.
        //div[@class='sg-row']//a[@class='a-link-normal a-text-normal']
        WebDriver driver = null;
        List<WebElement> list_product_links = driver.findElements(By.xpath("//div[@class='sg-row']//a[@class='a-link-normal a-text-normal']"));

        //This will give all the prices corresponding to the above products.
        //We are assuming, that indexes of above product links matches the price list indexes in below list.
        //Most of the time this assumption is right. We need to inspect the element to check the assumption is right.
        //In this case this assumption certainly is right.
        List<WebElement> list_product_prices = driver.findElements(By.xpath("//div[@class='sg-row']//span[@class='a-price-whole']"));

        int product_link_index = -1;// this value is kept negative, to check later
        //Loop through the List
        for (int i=0;i< list_product_prices.size();i++){
            //Value is to be captured, then , (comma) is to be removed and then it is to be converted to a integer.
            //Below all done in a single step and value stored in temp variable
            int temp = Integer.parseInt(list_product_prices.get(i).getText().replace(",",""));
            if (temp<product_price_limit){// if product is less then the price mentioned
                product_link_index = i;
                scn.log("Product found with in the price range. ");
                break;
            }
        }

        //If no product is found in the above loop.
        if (product_link_index==-1){
            scn.log("No product found with in the price range");
            Assert.fail("No product found on page 1 which has price less then mentioned amount");
        }

        //if a product with required price is found then click on the link.
        //Save the name of the Product
        String product_text = list_product_links.get(product_link_index).getText();
        scn.log("Product found with in the price range: " + product_text);
        list_product_links.get(product_link_index).click();

        //Product description page will be opened
        product_description_is_displayed_in_new_tab();
        scn.log("Product Description is displayed in new tab.");

        //On Product Description Page Select Quantity as mentioned in the feature file
        testContext.productDescriptionPageObjects.selectQuantity(product_quantity);
        scn.log("Quantity Selected. " + product_quantity);

        //Click on add to cart Button on product Description Page
        testContext.productDescriptionPageObjects.clickOnAddToCartButton();
        scn.log("Add to cart to button clicked.");

		/*
	        Note: Checking Added to Cart Text is displayed is not enough to check the feature.
	                You need to add more validation steps like:
	                a. Checking that the right product is added by matching the text of the product
	                b. Check the right price is displayed.
	                c. If quantity is more than 1, then price is to be multiplied by the quantity and validated.
	                I am skipping this validation steps and leaving up to the student to implement it.
	                Some more complex logic has to be written to implement these validations.
	                I recommend students is to try writing this logic as well, at least the price logic should be there.
		 */
        //Checking the Added to Cart Text is displayed
        testContext.productDescriptionPageObjects.checkAddedToCartMessageIsDisplayed();
        scn.log("Add to cart message is displayed");

        //Close the open Product Descp page.
        //Notice we are using driver.close and not driver.quit.
        //Driver.close will only close product description tab
        driver.close();
        scn.log("Product description tab is closed.");

        //Clean up for this method is to switch to the original window
        //Because we need to search new product there again
        //However you can continue to use the same window to search for new products

        scn.log("Driver switched to original tab/window");
    }

    private void product_description_is_displayed_in_new_tab() {
    }


    @When("User enters minimum price as {string} and maximum price as {string} mentioned in below table")
    public void user_enters_minimum_price_as_and_maximum_price_as_mentioned_in_below_table(String min, String max) {
        testContext.searchPageObjects.FilterSearchResultByPrice(min,max);
    }

    @Then("Verify that Search results gets filtered with price range between {int} and {int}")
    public void search_results_gets_filtered_with_price_range_between_and(int min, int max) {
        testContext.searchPageObjects.VerifyThatSearchedProductsAreInPriceRange(min,max);
    }



}
