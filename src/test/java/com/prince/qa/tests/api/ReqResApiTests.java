package com.prince.qa.tests.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ReqResApiTests {
    private String apiKey;

    @BeforeClass
    public void configureApi() {
        RestAssured.baseURI = System.getProperty("reqresBaseUrl", "https://reqres.in");
        apiKey = System.getenv("REQRES_API_KEY");
    }

    private io.restassured.specification.RequestSpecification request() {
        var spec = given().accept(ContentType.JSON).contentType(ContentType.JSON);
        if (apiKey != null && !apiKey.isBlank()) {
            spec.header("x-api-key", apiKey);
        }
        return spec;
    }

    @Test(description = "GET users page 2 returns a valid user collection")
    public void getUsersPageTwo() {
        Response response = request().queryParam("page", 2).when().get("/api/users").then().extract().response();
        Assert.assertEquals(response.statusCode(), 200, response.asString());
        Assert.assertEquals(response.jsonPath().getInt("page"), 2);
        Assert.assertTrue(response.jsonPath().getInt("per_page") > 0);
        Assert.assertTrue(response.jsonPath().getInt("total") > 0);
        Assert.assertTrue(response.jsonPath().getInt("total_pages") >= 2);
        Assert.assertTrue(response.jsonPath().getList("data").size() > 0);
        Assert.assertNotNull(response.jsonPath().getInt("data[0].id"));
        Assert.assertTrue(response.jsonPath().getString("data[0].email").contains("@"));
        Assert.assertNotNull(response.jsonPath().getString("data[0].first_name"));
        Assert.assertNotNull(response.jsonPath().getString("data[0].last_name"));
        Assert.assertNotNull(response.jsonPath().getString("data[0].avatar"));
    }

    @Test(description = "POST users creates a response with echoed fields and generated metadata")
    public void createUser() {
        Map<String, String> payload = new HashMap<>();
        payload.put("name", "Prince Kumar");
        payload.put("job", "QA Engineer");

        Response response = request().body(payload).when().post("/api/users").then().extract().response();
        Assert.assertEquals(response.statusCode(), 201, response.asString());
        Assert.assertEquals(response.jsonPath().getString("name"), "Prince Kumar");
        Assert.assertEquals(response.jsonPath().getString("job"), "QA Engineer");
        Assert.assertNotNull(response.jsonPath().getString("id"));
        Assert.assertNotNull(response.jsonPath().getString("createdAt"));
    }

    @Test(description = "POST login without password returns a validation response")
    public void loginWithoutPasswordReturnsValidationError() {
        Map<String, String> payload = new HashMap<>();
        payload.put("email", "peter@klaven");

        Response response = request().body(payload).when().post("/api/login").then().extract().response();
        Assert.assertEquals(response.statusCode(), 400, response.asString());
        Assert.assertNotNull(response.jsonPath().getString("error"));
    }

    @Test(description = "GET unknown user returns 404")
    public void unknownUserReturnsNotFound() {
        Response response = request().when().get("/api/users/23").then().extract().response();
        Assert.assertEquals(response.statusCode(), 404, response.asString());
        Assert.assertTrue(response.asString().isBlank() || response.jsonPath().getMap("$").isEmpty(),
                "A missing classic user should return an empty response object");
    }
}
