package org.example;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.net.URI;

import static io.restassured.RestAssured.given;

public class Config {
    static URI getInvoiceURI;
    static URI addInvoiceURI;
    static String newInvoiceBody;
    static RequestSpecification browserParams;

    @BeforeClass
    public void setUp() {
        addInvoiceURI = URI.create("https://stg-product.ggbnk.com/api/v1/users/invoices");
        getInvoiceURI = URI.create("https://stg-product.ggbnk.com/api/v1/users/invoices");
        browserParams = given()
                .contentType("application/json")
                .header("public-key", "GG38163151688523")
                .header("private-key", "77a1567e-4d5d-4471-9fcd-2ee34a267d3a");
        newInvoiceBody = "{"
                + "\"orderId\": \"%s\","
                + "\"recipient\": \"GG38163151688523\","
                + "\"amount\": \"10\","
                + "\"description\": \"test invoice\","
                + "\"expiration\": \"%d\","
                + "\"serverUrl\": \"https://www.webhook/\","
                + "\"resultUrl\": \"https://www.webhook/\","
                + "\"feeByUser\": true,"
                + "\"preferredMopType\": null"
                + "}";
    }

    @AfterClass
    public void tearDown() {
        RestAssured.reset();
    }
}
