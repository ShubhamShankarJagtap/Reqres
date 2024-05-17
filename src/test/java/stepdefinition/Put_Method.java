package stepdefinition;

import Utility.BaseClass;
import Utility.Employee;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Put_Method extends BaseClass {

    Employee employee;
    ObjectMapper objectMapper;

    @Given("I set the request structure for put api.")
    public void iSetTheRequestStructureForPutApi(DataTable dataTable) {

        Map<String, String> request_structure = dataTable.asMap(String.class, String.class);
        objectMapper = new ObjectMapper();
        System.out.println("The firstName is : " + request_structure.get("name"));
        System.out.println("The job is : " + request_structure.get("job"));
        ObjectNode request_structure_node = objectMapper.createObjectNode();
        request_structure_node.put("firstName", request_structure.get("name"));
        request_structure_node.put("job", request_structure.get("job"));

//        print all fields

        Iterator<String> fields = request_structure_node.fieldNames();
        while (fields.hasNext()) {
            String field = fields.next();
            System.out.println(field);
        }

//    print all the values.

        request_structure_node.elements().forEachRemaining(System.out::println);

//        print all the fields and values together.
        Iterator<Map.Entry<String, JsonNode>> all = request_structure_node.fields();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = all.next();
            System.out.println(field.getKey() + ": " + field.getValue());
        }
    }

    @Then("I verify the response for the put api.")
    public void iVerifyTheResponseForThePutApi() {

//        Verify the response.

        employee = response.as(Employee.class);
        String actualName = response.jsonPath().getString("name");
        String actualJob = response.jsonPath().getString("job");
//        id = response.jsonPath().getInt("id");

        String expectedName = employee.getFirstName();
        String expectedJob = employee.getJob();

//        Verify the firstName
        Assert.assertEquals(actualName, expectedName, "The name of the employee is not the same");
        Assert.assertEquals(actualJob, expectedJob, "The job of the employee is not the same");

        System.out.println("Verify the response for the put api passed.");

    }

    @When("I hit a put api for user {int}.")
    public void iHitAPutApiForUser(int userID) {

        requestSpecification = RestAssured.given();

        requestSpecification.baseUri("https://reqres.in")
                .basePath("/api/")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .pathParam("id", userID)
                .body(objectMapper)
                .log()
                .all();

        response = requestSpecification.when().put("/users" + "/" + "{id}")
                .then().assertThat().statusCode(200)
                .extract().response();

        response.prettyPrint();
    }
}
