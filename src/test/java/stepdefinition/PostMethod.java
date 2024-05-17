package stepdefinition;

import Utility.BaseClass;
import Utility.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import org.testng.Assert;


import java.util.Iterator;
import java.util.Map;

public class PostMethod extends BaseClass {

    int userId;
    Employee employee;
    Map<String, String> map;
    ObjectMapper objectMapper;

    @Given("I set up the request structure to create an user.")
    public void iSetUpTheRequestStructureToCreateAnUser(DataTable dataTable) {

        map = dataTable.asMap(String.class, String.class);
    }

    @When("I hit an api")
    public void iHitAnApi() {

        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://reqres.in/")
                .basePath("/api/")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(map);

        response = requestSpecification.when()
                .post("/users")
                .then().assertThat().statusCode(201)
                .log()
                .all()
                .extract().response();

        response.prettyPrint();
    }

    @Then("I verify the name as {string} and job as {string}")
    public void iVerifyTheNameAsAndJobAs(String expectedName, String expectedJobTitle) {

        userId = response.jsonPath().getInt("id");
        System.out.println("User ID: " + userId);

        String actualName = response.jsonPath().getString("name");
        String actualJobTitle = response.jsonPath().getString("job");

        Assert.assertEquals(actualName, expectedName, "Name is not the same");
        Assert.assertEquals(actualJobTitle, expectedJobTitle, "Title is not the same");

        System.out.println("Verification is completed");
    }

//    @Given("I set up the request structure for class object")
//    public void iSetUpTheRequestStructureForClassObject() {
//
//        employee = new Employee();
//        employee.setFirstName("Shubham");
//        employee.setJob("Software Engineering");
//
//        requestSpecification = RestAssured.given()
//                .baseUri("https://reqres.in/")
//                .basePath("api/")
//                .header("Content-Type", "application/json")
//                .header("Accept", "application/json")
//                .body(employee)
//                .log().all();
//
//        response = requestSpecification.when().post("users");
//        response.prettyPrint();
//    }

    @Then("I verify the response")
    public void iVerifyTheResponse() {

        String expectedName = employee.getFirstName();
        String expectedJobTitle = employee.getJob();

        String actualName = response.jsonPath().getString("name");
        String actualJobTitle = response.jsonPath().getString("job");

        Assert.assertEquals(expectedName, actualName, "Name is not the same");
        Assert.assertEquals(expectedJobTitle, actualJobTitle, "Title is not the same");

        System.out.println(employee.getId());
        System.out.println("Verification is completed and successfully retrieved.");
    }


    @Given("I set the request structure using ObjectMapper.")
    public void iSetTheRequestStructureUsingObjectMapper() {

        objectMapper = new ObjectMapper();

//        Creating the object(JSON) node.

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("firstName", "Shubham");
        objectNode.put("job", "Software Engineering");

        try {
            String userDetails = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectNode);
            System.out.println(userDetails);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String firstName = objectNode.get("firstName").asText();
        String job = objectNode.get("job").asText();

        System.out.println("First Name: " + firstName);
        System.out.println("Job: " + job);

//        For printing the all fields
        System.out.println("print all the fieldNames---------/n");
        Iterator<String> fieldNames = objectNode.fieldNames();

        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            System.out.println(fieldName);
        }

//        For printing all the values
        objectNode.elements().forEachRemaining(System.out::println);

//        printing both the values & keys.

      Iterator<Map.Entry<String, JsonNode>> fields  = objectNode.fields();

      while (fields.hasNext()) {
          Map.Entry<String, JsonNode> field = fields.next();
          System.out.println(field.getKey() + ": " + field.getValue());
      }

    }

    @When("I hit an api for an ObjectMapper.")
    public void iHitAnApiForAnObjectMapper() {

        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://reqres.in/")
                .basePath("/api/")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(objectMapper);

        response = requestSpecification.when()
                .post("/users")
                .then().assertThat().statusCode(201)
                .log()
                .all()
                .extract().response();

        employee = response.as(Employee.class);
        response.prettyPrint();
    }
}
