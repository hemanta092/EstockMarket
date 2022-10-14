package com.stockmarket.query.dto.response;

import com.stockmarket.query.dto.AggregateDto;
import com.stockmarket.query.dto.StockDto;

import java.util.List;

public class StockResponse
{

private String message;	
	
	private Boolean isSuccessful;
	
	private String error;
	
	private List<StockDto> stockDtoList;

	private AggregateDto aggregateDto;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getIsSuccessful() {
		return isSuccessful;
	}

	public void setIsSuccessful(Boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public List<StockDto> getStockDtoList() {
		return stockDtoList;
	}

	public void setStockDtoList(List<StockDto> stockDtoList) {
		this.stockDtoList = stockDtoList;
	}

	public AggregateDto getAggregateDto() {
		return aggregateDto;
	}

	public void setAggregateDto(AggregateDto aggregateDto) {
		this.aggregateDto = aggregateDto;
	}

}
