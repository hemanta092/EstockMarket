package com.stockmarket.query.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustExceptionHandler
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CustExceptionHandler.class);

    @ExceptionHandler(value = CompanyExistsException.class)
    public ResponseEntity<Object> exception(CompanyExistsException companyExistsException)
    {
        LOGGER.info("Company exists");
        return new ResponseEntity<>("Company exists", HttpStatus.NOT_FOUND);
    }
}
