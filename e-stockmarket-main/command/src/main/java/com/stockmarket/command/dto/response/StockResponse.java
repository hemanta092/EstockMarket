package com.stockmarket.command.dto.response;

import com.stockmarket.command.dto.StockDto;

public class StockResponse
{

private String message;	
	
	private Boolean isSuccessful;
	
	private String error;
	
	private StockDto stockDto;

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

	public StockDto getStockDto() {
		return stockDto;
	}

	public void setStockDto(StockDto stockDto) {
		this.stockDto = stockDto;
	}
	
	


}
