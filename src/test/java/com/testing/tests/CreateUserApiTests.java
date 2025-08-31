package com.testing.tests;

import com.testing.base.BaseTest;
import com.testing.utils.ApiClient;
import com.testing.utils.Payloads;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateUserApiTests extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserApiTests.class);

    @Test(description = "Create a user")
    @Description("Verify that a user can be created and the response contains the correct name.")
    public void testCreateUser() {
        logger.info("[TEST][START] testCreateUser");
        try {
            // Test user data (simulate a real user for login integration)
            String username = "testuser";
            String email = "testuser@example.com";
            String password = "testpassword";
            String name = "morpheus";
            String job = "leader";

            // Store for login test
            UserTestData.username = username;
            UserTestData.email = email;
            UserTestData.password = password;
            UserTestData.name = name;
            UserTestData.job = job;

            // Optionally, send all user fields if your API supports it
            // If not, just send name and job as per ReqRes API
            String body = Payloads.createUserPayload(name, job);
            Response response = ApiClient.post("/users", body);

            Assert.assertEquals(response.getStatusCode(), 201);
            Assert.assertEquals(response.jsonPath().getString("name"), name);
            Assert.assertEquals(response.jsonPath().getString("job"), job);
            Assert.assertNotNull(response.jsonPath().getString("id"));
            Assert.assertNotNull(response.jsonPath().getString("createdAt"));

            // Store returned id and createdAt
            UserTestData.id = response.jsonPath().getString("id");
            UserTestData.createdAt = response.jsonPath().getString("createdAt");

            // Print the created user data for debug
            System.out.println("\n==== Created User Data ====");
            System.out.println("username: " + username);
            System.out.println("email: " + email);
            System.out.println("password: " + password);
            System.out.println("name: " + name);
            System.out.println("job: " + job);
            System.out.println("id: " + UserTestData.id);
            System.out.println("createdAt: " + UserTestData.createdAt);
            System.out.println("==========================\n");

            logger.info("[TEST][PASS] testCreateUser");
        } catch (AssertionError | Exception e) {
            logger.error("[TEST][FAIL] testCreateUser - {}", e.getMessage(), e);
            throw e;
        }
    }
}
