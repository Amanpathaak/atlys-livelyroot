package com.atlys.api.tests;

import com.atlys.common.TestConfig;
import com.atlys.api.client.SearchApiClient;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import io.restassured.response.Response;

@Test(groups = {"api"})
public class SearchApiTests {

    private TestConfig cfg;
    private WireMockServer wireMock;
    private SearchApiClient client;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        cfg = new TestConfig();

        if (cfg.isMockEnabled()) {
            wireMock = new WireMockServer(WireMockConfiguration.options().port(8089));
            wireMock.start();

            // Stub: valid query -> 200 with list
            wireMock.stubFor(get(urlPathEqualTo("/getwidgets"))
                    .withQueryParam("q", matching("(?i)(monstera|snake\\s*plant)"))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody("{\"widgets\":[{\"id\":1,\"name\":\"Monstera Deliciosa\"},{\"id\":2,\"name\":\"Snake Plant\"}]}")));

            // Stub: missing q -> 400
            wireMock.stubFor(get(urlEqualTo("/getwidgets"))
                .atPriority(1)
                .willReturn(aResponse()
                    .withStatus(400)
                    .withHeader("Content-Type", "application/json")
                    .withBody("{\"error\":\"Missing required query parameter 'q'\"}")));

            // Stub: valid but no matches -> 200 empty
            wireMock.stubFor(get(urlPathEqualTo("/getwidgets"))
                    .withQueryParam("q", matching(".*zzz.*"))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody("{\"widgets\":[]}")));
        }

        client = new SearchApiClient(cfg.getApiBaseUri());
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (wireMock != null) {
            wireMock.stop();
        }
    }

    @Test(description = "API-01: 200 OK with valid query and schema validation")
    public void testGetWidgetsValidQuery() {
        Response resp = client.getWidgets("monstera");
        Assert.assertEquals(resp.statusCode(), 200, "Status should be 200 OK");
        resp.then().assertThat().body(matchesJsonSchemaInClasspath("jsonschemas/widgets-schema.json"));
        Assert.assertTrue(resp.jsonPath().getList("widgets").size() > 0, "Expected at least one widget");
    }

    @Test(description = "API-02: 400 Bad Request when 'q' is missing")
    public void testGetWidgetsMissingQueryParam() {
        Response resp = client.getWidgets(null);
        Assert.assertEquals(resp.statusCode(), 400, "Status should be 400 Bad Request");
        Assert.assertTrue(resp.asString().contains("Missing"), "Error payload should mention missing parameter");
    }

    @Test(description = "API-03: 200 with empty list when query yields no matches")
    public void testGetWidgetsNoMatches() {
        Response resp = client.getWidgets("zzzxqwy");
        Assert.assertEquals(resp.statusCode(), 200, "Status should be 200 OK even if no matches");
        Assert.assertEquals(resp.jsonPath().getList("widgets").size(), 0, "Expected empty list of widgets");
    }
}
