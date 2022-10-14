package com.stockmarket.command.exception;

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

    @ExceptionHandler(value = MissingFieldsException.class)
    public ResponseEntity<Object> exception(MissingFieldsException exception)
    {
        LOGGER.info("Missing Mandatory Field/s");
        return new ResponseEntity<>("Missing Mandatory Field/s", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CompanyTurnOverException.class)
    public ResponseEntity<Object> exception(CompanyTurnOverException exception)
    {
        LOGGER.info("Company Turnover must be greater than 10cr");
        return new ResponseEntity<>("Company Turnover must be greater than 10cr", HttpStatus.NOT_FOUND);
    }

}
