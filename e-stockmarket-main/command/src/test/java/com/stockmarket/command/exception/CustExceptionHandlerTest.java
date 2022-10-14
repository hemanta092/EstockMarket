package com.stockmarket.command.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustExceptionHandlerTest {

    private final CustExceptionHandler handler = new CustExceptionHandler();


    @Test
    void testCompanyExists() throws Exception {

        final ResponseEntity<Object> handled = handler.exception(new CompanyExistsException());
        assertEquals("Company exists", handled.getBody());
    }

    @Test
    void testMissingFields() throws Exception {

        final ResponseEntity<Object> handled = handler.exception(new MissingFieldsException());
        assertEquals("Missing Mandatory Field/s", handled.getBody());
    }

    @Test
    void testCompanyTurnOver() throws Exception {

        final ResponseEntity<Object> handled = handler.exception(new CompanyTurnOverException());
        assertEquals("Company Turnover must be greater than 10cr", handled.getBody());
    }
}