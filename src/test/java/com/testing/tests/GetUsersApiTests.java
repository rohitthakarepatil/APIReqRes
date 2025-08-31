package com.testing.tests;

import com.testing.base.BaseTest;
import com.testing.utils.ApiClient;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GetUsersApiTests extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(GetUsersApiTests.class);

    @Test(description = "Get list of users")
    @Description("Verify that the list of users endpoint returns a 200 and non-empty data array.")
    public void testGetUsers() {
        logger.info("[TEST][START] testGetUsers");
        try {
            Response response = ApiClient.get("/users?page=2");
            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertFalse(response.jsonPath().getList("data").isEmpty());
            Assert.assertEquals(response.jsonPath().getInt("page"), 2);
            Assert.assertTrue(response.jsonPath().getInt("per_page") > 0);
            Assert.assertTrue(response.jsonPath().getInt("total") >= response.jsonPath().getList("data").size());
            logger.info("[TEST][PASS] testGetUsers");
        } catch (AssertionError | Exception e) {
            logger.error("[TEST][FAIL] testGetUsers - {}", e.getMessage(), e);
            throw e;
        }
    }
}
