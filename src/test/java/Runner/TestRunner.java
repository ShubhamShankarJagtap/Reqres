package Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(

        glue = "stepdefinition",
        features = ".//src/test/resources/Put.feature",
        monochrome = true,
        dryRun = false,
//        tags = "@UsingObjectMapper",
        plugin = {"pretty", "html:Report/cucumber_report.htm"}
)

public class TestRunner {
}
