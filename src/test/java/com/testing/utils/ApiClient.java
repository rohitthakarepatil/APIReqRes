package com.testing.utils;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.testing.config.Config;
import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import java.util.Map;

public class ApiClient {
    private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);

    public static Response get(String endpoint) {
        return get(endpoint, null, null);
    }

    public static Response get(String endpoint, Map<String, String> headers, Map<String, ?> queryParams) {
        logger.info("[API][GET] Endpoint: {}", endpoint);
        io.restassured.specification.RequestSpecification req = given().baseUri(Config.BASE_URL).contentType(JSON);
        if (Config.API_KEY != null && !Config.API_KEY.isEmpty()) {
            req.header(Config.API_KEY_HEADER, Config.API_KEY);
        }
        if (headers != null) {
            req.headers(headers);
        }
        if (queryParams != null) {
            req.queryParams(queryParams);
        }
        Response response = req.when().get(endpoint);
        logResponse(response);
        return response;
    }

    public static Response post(String endpoint, Object body) {
        return post(endpoint, body, null, null);
    }

    public static Response post(String endpoint, Object body, Map<String, String> headers, Map<String, ?> queryParams) {
        logger.info("[API][POST] Endpoint: {} | Payload: {}", endpoint, body);
        io.restassured.specification.RequestSpecification req = given().baseUri(Config.BASE_URL).contentType(JSON);
        if (Config.API_KEY != null && !Config.API_KEY.isEmpty()) {
            req.header(Config.API_KEY_HEADER, Config.API_KEY);
        }
        if (headers != null) {
            req.headers(headers);
        }
        if (queryParams != null) {
            req.queryParams(queryParams);
        }
        Response response = req.body(body).when().post(endpoint);
        logResponse(response);
        return response;
    }

    private static void logResponse(Response response) {
        logger.info("[API][Response] Status: {} | Body: {}", response.getStatusCode(), response.asString());
    }
}
