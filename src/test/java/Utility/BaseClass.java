package Utility;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;

public class BaseClass {

    static protected RequestSpecification requestSpecification;
    static protected Response response;

    protected void getRequest(Map<String, String> headers, String endPoint){

        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://reqres.in")
                .basePath("/api/")
                .header( "Accept", headers.get("Accept"))
                .header( "Content-Type", headers.get("Content-Type"))
                .log().all();

        response = requestSpecification
                .get(endPoint)
                .then().extract().response();
        response.then().statusCode(200);
        response.prettyPrint();
    }

    protected void getRequestWithPathParameter(Map<String, String> headers, String endPoint, String param){

        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://reqres.in")
                .basePath("/api/")
                .header( "Accept", headers.get("Accept"))
                .header( "Content-Type", headers.get("Content-Type"))
                .pathParam("user", param)
                .log().all();

        System.out.println(endPoint);
        response = requestSpecification
                .get(endPoint + "/" + "{user}")
                .then().extract().response();
        response.then().statusCode(200);
        response.prettyPrint();
    }

    protected void getRequestWithQueryParameter(Map<String, String> headers, String endPoint, String query){

        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://reqres.in")
                .basePath("/api/")
                .header( "Accept", headers.get("Accept"))
                .header( "Content-Type", headers.get("Content-Type"))
                .queryParam("page", Integer.parseInt(query))
                .log().all();

        response = requestSpecification
                .get(endPoint)
                .then().extract().response();
        response.then().statusCode(200);
        response.prettyPrint();
    }
}
