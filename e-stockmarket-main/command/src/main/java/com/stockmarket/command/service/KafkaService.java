package com.stockmarket.command.service;

import com.stockmarket.command.dto.CompanyDto;
import com.stockmarket.command.dto.StockDto;
import com.stockmarket.command.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaService.class);

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	
	public void deleteCompanyEvent(CompanyDto companydto) throws JsonProcessingException
	{
		String company = new ObjectMapper().writer().writeValueAsString(companydto);
		kafkaTemplate.send("deleteCompany",company);
	}


	public void createCompanyEvent(CompanyDto companyDto) throws JsonProcessingException {
		String company = new ObjectMapper().writer().writeValueAsString(companyDto);
		kafkaTemplate.send("createCompany",company);
	}

	public void deleteStockEvent(StockDto stockDto) throws JsonProcessingException
	{
		String stock = new ObjectMapper().writer().writeValueAsString(stockDto);
		kafkaTemplate.send("deleteStock",stock);
	}


	public void createStockEvent(StockDto stockDto) throws JsonProcessingException {
		String stock = new ObjectMapper().writer().writeValueAsString(stockDto);
		kafkaTemplate.send("createStock",stock);
	}

	public void deleteUserEvent(UserDto userDto) throws JsonProcessingException
	{
		String user = new ObjectMapper().writer().writeValueAsString(userDto);
		kafkaTemplate.send("deleteUser",user);
	}


	public void createUserEvent(UserDto userDto) throws JsonProcessingException {
		String user = new ObjectMapper().writer().writeValueAsString(userDto);
		kafkaTemplate.send("createUser",user);
	}

}
