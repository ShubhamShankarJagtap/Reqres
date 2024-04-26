package Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(

        glue = "stepdefinition",
        features = ".//src/test/resources/Get.feature",
        monochrome = true,
        dryRun = false,
        tags = "@Resource",
        plugin = {"pretty", "html:Report/cucumber_report.htm"}
)

public class TestRunner {
}
