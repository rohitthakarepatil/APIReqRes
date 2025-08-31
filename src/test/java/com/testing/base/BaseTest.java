package com.testing.base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import com.testing.config.Config;

public class BaseTest {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = Config.BASE_URL;
    }
}
