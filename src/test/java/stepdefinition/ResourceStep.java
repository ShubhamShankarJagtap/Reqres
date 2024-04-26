package stepdefinition;

import Utility.BaseClass;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class ResourceStep extends BaseClass {
    @Given("I set up the request structure to fetch the resource")
    public void iSetUpTheRequestStructureToFetchTheResource(DataTable dataTable) {

        Map<String, String> map = dataTable.asMaps().get(0);
        System.out.println(map.get("path"));

        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://reqres.in/api/")
                .accept(ContentType.JSON)
                .pathParam("resource", map.get("path"))
                .log()
                .all();

        response = requestSpecification.when().get("{resource}")
                .then()
                .statusCode(200)
                .extract().response();

        response.prettyPrint();
    }

    @Then("I verify the total dataSet is {int}.")
    public void iVerifyTheTotalDataSetIs(int expectedDataSet) {

        List<Map<String, String>> dataList = response.jsonPath().get("data");
        int actualDataSet = dataList.size();
        Assert.assertEquals(actualDataSet, expectedDataSet);
        System.out.println("Successfully verified the total data set.");
    }
}
