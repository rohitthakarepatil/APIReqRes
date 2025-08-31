package com.testing.tests;

import com.testing.base.BaseTest;
import com.testing.utils.ApiClient;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class GetUsersDynamicApiTests extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(GetUsersDynamicApiTests.class);

    @Test(description = "Get users with dynamic query params and headers")
    @Description("Fetch users with dynamic query parameters and custom headers.")
    public void testGetUsersDynamic() {
        logger.info("[TEST][START] testGetUsersDynamic");
        try {
            Map<String, Object> queryParams = new java.util.HashMap<>();
            queryParams.put("page", 2);
            Map<String, String> headers = new java.util.HashMap<>();
            headers.put("Custom-Header", "TestValue");
            Response response = ApiClient.get("/users", headers, queryParams);
            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertFalse(response.jsonPath().getList("data").isEmpty());
            Assert.assertEquals(response.jsonPath().getInt("page"), 2);
            Assert.assertTrue(response.jsonPath().getInt("per_page") > 0);
            Assert.assertTrue(response.jsonPath().getInt("total") >= response.jsonPath().getList("data").size());

            // Deserialize and pretty print the response
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            Object json = mapper.readValue(response.asString(), Object.class);
            String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            System.out.println("\n==== API Response (Pretty JSON) ====");
            System.out.println(prettyJson);
            System.out.println("====================================\n");

            logger.info("[TEST][PASS] testGetUsersDynamic");
        } catch (AssertionError | Exception e) {
            logger.error("[TEST][FAIL] testGetUsersDynamic - {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
