package com.sistema.bancario.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiIntegrationTests {

    @LocalServerPort
    private int port;

    @BeforeAll
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    public void testTransferenciaEndpoint() {
        given()
            .auth().basic("user", "password")
            .contentType("application/json")
            .body("{\"contaOrigem\": \"123\", \"contaDestino\": \"456\", \"valor\": 100}")
        .when()
            .post("/api/transferencia")
        .then()
            .statusCode(200)
            .body("status", equalTo("SUCESSO"));
    }
} 