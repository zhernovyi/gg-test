package org.example;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.net.URI;

import static org.example.Steps.*;

public class GgApiTests extends Config {
    Response response;
    URI uri;


    @Test
    public void newInvoiceTest() {
        response = addNewInvoiceStep(100);
        checkCreatedInvoiceStep(response);
    }

    @Test
    public void addInvoiceWithoutAuthTest() {
        addNewInvoiceWithoutAuthStep();
    }

    @Test
    public void checkGeneratedInvoiceLinkTest() {
        response = addNewInvoiceStep(100);
        uri = getGenerateInvoiceLinkStep(response);
        verifyGeneratedInvoiceLinkStep(uri);
    }

    @Test
    public void verifyCreationInvoiceWithLittleExpiration() throws InterruptedException {
        response = addNewInvoiceStep(0);
        checkExpiredWalletStep(response);
    }
}