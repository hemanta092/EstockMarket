package com.stockmarket.query.eventlistner;

import com.stockmarket.query.dto.StockDto;
import com.stockmarket.query.service.StockService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class StockListner
{
	private static final Logger LOGGER = LoggerFactory.getLogger(StockListner.class);
	@Autowired
	private StockService stockService;

	@KafkaListener(topics = "deleteStock", groupId = "group_id")
	public void consumeDelete(String message) throws JsonMappingException, JsonProcessingException {
		LOGGER.info(String.format("$$$ -> Consumed Delete Stock Message -> %s",message));
		StockDto stock = new ObjectMapper().readValue(message, StockDto.class);
		Boolean isstockDeleted = stockService.deleteStock(stock.getId());
		LOGGER.info("isstockDeleted - {}",isstockDeleted);

	}

	@KafkaListener(topics = "createStock", groupId = "group_id")
	public void consumeCreate(String message) throws JsonMappingException, JsonProcessingException{
		LOGGER.info(String.format("$$$ -> Consumed Create Stock Message -> %s",message));
		StockDto stock = new ObjectMapper().readValue(message, StockDto.class);
		StockDto savedstock = stockService.createStock(stock);
		LOGGER.info("saved stock{}",savedstock);

	}

}
