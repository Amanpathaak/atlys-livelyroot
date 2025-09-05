package com.atlys.api.client;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class SearchApiClient {

    public SearchApiClient(String baseUri) {
        RestAssured.baseURI = baseUri;
    }

    public Response getWidgets(String query) {
        if (query == null) {
            return RestAssured.given()
                    .when()
                    .get("/getwidgets")
                    .andReturn();
        }
        return RestAssured.given()
                .queryParam("q", query)
                .when()
                .get("/getwidgets")
                .andReturn();
    }
}
