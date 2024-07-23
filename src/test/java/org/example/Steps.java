package org.example;

import io.restassured.response.Response;

import java.net.URI;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Steps extends Config {
    public static Response addNewInvoiceStep(int expiration) {
        String newIdFromTimeStamp = Long.toString(new Date().getTime());

        Response response = given()
                .spec(browserParams)
                .body(getBodyWithUniqueIdStep(newInvoiceBody, newIdFromTimeStamp, expiration))

                .when()
                .post(addInvoiceURI);

        response
                .then()
                .log().all()
                .statusCode(200)
                .body("order_id", equalTo(newIdFromTimeStamp));

        return response;

    }

    public static void addNewInvoiceWithoutAuthStep() {
        String newIdFromTimeStamp = Long.toString(new Date().getTime());

        Response response = given()
                .body(getBodyWithUniqueIdStep(newInvoiceBody, newIdFromTimeStamp, 100))

                .when()
                .post(addInvoiceURI);

        response
                .then()
                .log().all()
                .statusCode(401)
                .body("message", equalTo("Unauthorized"));

    }

    public static String getBodyWithUniqueIdStep(String body, String timestamp, int expiration) {
        return String.format(body, timestamp, expiration);
    }

    public static void checkCreatedInvoiceStep(Response response) {
        String newInvoiceId = "/" + response.jsonPath().getString("id");
        given()
                .spec(browserParams)

                .when()
                .get(getInvoiceURI + newInvoiceId)

                .then().statusCode(200).log().all();
    }

    public static void checkExpiredWalletStep(Response response) throws InterruptedException {
        String newInvoiceId = "/" + response.jsonPath().getString("id");
        Thread.sleep(12000);
        given()
                .spec(browserParams)

                .when()
                .get(getInvoiceURI + newInvoiceId)

                .then().statusCode(200)
                .body("state", equalTo("expired")).log().all();
    }

    public static URI getGenerateInvoiceLinkStep(Response response) {
        String newInvoiceId = "/" + response.jsonPath().getString("id");
        Response newResponse =  given()
                .spec(browserParams)

                .when()
                .get(getInvoiceURI + newInvoiceId);

        return URI.create(newResponse.jsonPath().getString("invoice_url"));
    }

    public static void verifyGeneratedInvoiceLinkStep(URI uri){
        given()
                .when().get(uri)
                .then().statusCode(200).log().all();
    }
}