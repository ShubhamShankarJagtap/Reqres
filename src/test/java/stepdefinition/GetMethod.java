package stepdefinition;

import Utility.BaseClass;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;

import java.util.*;

public class GetMethod extends BaseClass {
    @Given("I set the request structure")
    public void iSetTheRequestStructure(DataTable data) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        List<Map<String, String>> maps = data.asMaps(String.class, String.class);

        for (Map<String, String> map : maps) {
//             Integer path = Integer.parseInt(map.get("path"));
            if (!map.get("endpoint").isEmpty()) {
//               getRequest(headers, map.get("endpoint"));
//                getRequestWithPathParameter(headers, map.get("endpoint"), path);
                getRequestWithQueryParameter(headers, map.get("endpoint"), String.valueOf(map.get("queryParameter")));
            }
       }

        for (Map<String, String> map2 : maps) {

            System.out.println(map2.get("path"));
        }
    }

    @Then("I verify the page number is {int}")
    public void iVerifyThePageNumberIs(int expectedPageNumber) {

        int actualPageNumber = response.jsonPath().get("page");
        Assert.assertEquals(actualPageNumber, expectedPageNumber,"The page number is correct");

        List<Map<String, String>> maps = response.jsonPath().getList("data");
        Map<String, String> firstObject = maps.getFirst();

        if (Objects.nonNull(firstObject)){

            System.out.println(firstObject.get("page"));
        }

        System.out.println("successfully verified the page number is 2....");
    }

    @Then("I verify the id is {int}")
    public void iVerifyTheIdIs(int expectedId) {

        int actualId = response.jsonPath().get("data.id");
        Assert.assertEquals(actualId, expectedId, "The id is correct");
    }
}
