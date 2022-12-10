package com.edureka.userms;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/feature",
                glue = "com.edureka.userms.steps")
public class UsermsCucumberApplication {
}
