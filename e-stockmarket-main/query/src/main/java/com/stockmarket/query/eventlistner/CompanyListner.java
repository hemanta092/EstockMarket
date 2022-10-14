package com.stockmarket.query.eventlistner;

import com.stockmarket.query.dto.CompanyDto;
import com.stockmarket.query.service.CompanyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CompanyListner
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyListner.class);
    @Autowired
    private CompanyService companyService;

    @KafkaListener(topics = "deleteCompany", groupId = "group_id")
    public void consumeDelete(String message) throws JsonMappingException, JsonProcessingException {
        LOGGER.info(String.format("$$$ -> Consumed Delete Company Message -> %s",message));
        CompanyDto company = new ObjectMapper().readValue(message, CompanyDto.class);
        Boolean isCompanyDeleted = companyService.removeCompnay(company.getCompanyCode());
        LOGGER.info("isCompanyDeleted - ",isCompanyDeleted);

    }

    @KafkaListener(topics = "createCompany", groupId = "group_id")
    public void consumeCreate(String message) throws JsonMappingException, JsonProcessingException{
        LOGGER.info(String.format("$$$ -> Consumed Create Company Message -> %s",message));
        CompanyDto company = new ObjectMapper().readValue(message, CompanyDto.class);
        CompanyDto savedCompany = companyService.createCompany(company);
        LOGGER.info("saved Company{}",savedCompany);

    }

}
