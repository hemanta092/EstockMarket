package com.stockmarket.command.controller;

import com.stockmarket.command.dto.StockDto;
import com.stockmarket.command.dto.response.StockResponse;
import com.stockmarket.command.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/market/command/stock")
@CrossOrigin(origins="*")
public class StockController 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(StockController.class);

	@Autowired
	private StockService stockService;

	@PostMapping(value = "/registerstock")
	public ResponseEntity<StockResponse> registerStock(@RequestBody StockDto stockDto){
		LOGGER.info("Inside register stock controller");
		StockResponse response = new StockResponse();
		
		StockDto savedStock = stockService.createStock(stockDto);
		
		if(savedStock != null)
		{
			response.setIsSuccessful(true);
			response.setStockDto(stockDto);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		LOGGER.info("Exiting register stock controller");
		return new ResponseEntity<>(response,HttpStatus.NOT_IMPLEMENTED);
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<StockResponse> deleteStock(@PathVariable("id") Long id)
	{
		LOGGER.info("Exiting delete stock controller");
		StockResponse response = new StockResponse();
		boolean isSuccess = stockService.deleteStock(id);
		if(isSuccess) {
		response.setIsSuccessful(isSuccess);
		return new ResponseEntity<>(response,HttpStatus.OK);
		}
		LOGGER.info("Exiting delete stock controller");
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}



}
