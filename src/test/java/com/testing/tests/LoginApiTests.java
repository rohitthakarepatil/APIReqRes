package com.testing.tests;

import com.testing.base.BaseTest;
import com.testing.utils.ApiClient;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

public class LoginApiTests extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(LoginApiTests.class);

    @Test(description = "Login with valid ReqRes credentials")
    @Description("Verify that login endpoint returns a token for valid ReqRes credentials and pretty print the response.")
    public void testLogin() throws Exception {
        logger.info("[TEST][START] testLogin");
        try {
            // ReqRes only allows login with specific test credentials
            // See: https://reqres.in/api-docs/#/Login/post_login
            String email = "eve.holt@reqres.in";
            String password = "anypassword";

            Map<String, Object> loginPayload = new HashMap<>();
            loginPayload.put("email", email);
            loginPayload.put("password", password);

            Response response = ApiClient.post("/login", loginPayload);
            if (response.getStatusCode() != 200) {
                System.out.println("\n[DEBUG] Login failed. Status: " + response.getStatusCode());
                System.out.println("[DEBUG] Response body: " + response.asString());
            }
            Assert.assertEquals(response.getStatusCode(), 200, "Login status code should be 200");
            Assert.assertNotNull(response.jsonPath().getString("token"), "Token should not be null");

            // Deserialize and pretty print the response
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            Object json = mapper.readValue(response.asString(), Object.class);
            String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            System.out.println ("\n==== Login API Response (Pretty JSON) ====");
            System.out.println(prettyJson);
            System.out.println("==========================================\n");

            logger.info("[TEST][PASS] testLogin");
        } catch (AssertionError | Exception e) {
            logger.error("[TEST][FAIL] testLogin - {}", e.getMessage(), e);
            throw e;
        }
    }
}
