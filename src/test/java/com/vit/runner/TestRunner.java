package com.vit.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
@RunWith(Cucumber.class)
@CucumberOptions(
        features="classpath:features",//to tell cucumber where is ur feature file
        glue="com.vit.stepdefs", // to tell cucumber where is ur step def code
        tags="@healthcheck", // to tell which tagged feature file to execute
        plugin = {"pretty", // to generate reports
                "html:target/html/htmlreport.html",
                "json:target/json/file.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        publish = true,
        dryRun = true
)


    public class TestRunner {
    }

