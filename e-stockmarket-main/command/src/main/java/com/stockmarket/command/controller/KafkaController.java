package com.stockmarket.command.controller;

import com.stockmarket.command.dto.CompanyDto;
import com.stockmarket.command.dto.StockDto;
import com.stockmarket.command.service.CompanyService;
import com.stockmarket.command.service.KafkaService;
import com.stockmarket.command.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController 
{

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaController.class);

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private StockService stockService;

    @PostMapping(value = "/publish/deleteStock")
    public void sendMessageToDelete(@RequestBody StockDto stockDto) throws JsonProcessingException {
        LOGGER.info("Sending delete Stock Event");
        kafkaService.deleteStockEvent(stockDto);
    }

    @PostMapping(value = "/publish/createStock")
    public void sendMessageToCreate(@RequestBody StockDto stockDto) throws JsonProcessingException{
        LOGGER.info("Sending create Stock Event");
        kafkaService.createStockEvent(stockDto);
    }

    @PostMapping(value = "/publish/deleteCompany")
    public void sendMessageToDelete(@RequestBody CompanyDto companyDto) throws JsonProcessingException {
        LOGGER.info("Sending delete Company Event");
        kafkaService.deleteCompanyEvent(companyDto);
    }

    @PostMapping(value = "/publish/createCompany")
    public void sendMessageToCreate(@RequestBody CompanyDto companyDto) throws JsonProcessingException{
        LOGGER.info("Sending create Company Event");
        kafkaService.createCompanyEvent(companyDto);
    }


}
