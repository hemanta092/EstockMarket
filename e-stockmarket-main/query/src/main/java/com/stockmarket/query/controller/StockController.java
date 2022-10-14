package com.stockmarket.query.controller;

import com.stockmarket.query.dto.AggregateDto;
import com.stockmarket.query.dto.StockDto;
import com.stockmarket.query.service.CompanyService;
import com.stockmarket.query.service.StockService;
import com.stockmarket.query.dto.request.StockRequest;
import com.stockmarket.query.dto.response.StockResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1.0/market/query/stock")
public class StockController 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(StockController.class);

	@Autowired
	private StockService stockService;
	@Autowired
	private CompanyService companyService;


	@PostMapping(value = "/info")
	public ResponseEntity<StockResponse> fetchStockDetails(@RequestBody StockRequest request)
	{

		LOGGER.info("Inside fetch stock details controller");

		StockResponse response = new StockResponse();

		String companyCode = companyService.getCompanyCodeByName(request.getCompanyName());
		Date startDate = new Date(request.getStartDate());
		Date endDate = new Date(request.getEndDate());
		List<StockDto> savedStocks = stockService.getStocks(companyCode,startDate,endDate);
		AggregateDto aggregateDto = stockService.getAggregateStock(companyCode,startDate,endDate);
		if(savedStocks != null && !savedStocks.isEmpty())
		{
			response.setIsSuccessful(true);
			response.setStockDtoList(savedStocks);
			response.setAggregateDto(aggregateDto);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		LOGGER.info("Exiting fetch stock details controller");

		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}

}
