package com.stockmarket.command.dto.response;


import com.stockmarket.command.dto.CompanyDto;

public class CompanyResponse
{
	
	private String message;	
	
	private Boolean isSuccessful;
	
	private String error;
	
	private CompanyDto companyDto;

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

	public CompanyDto getCompanyDto() {
		return companyDto;
	}

	public void setCompanyDto(CompanyDto companyDto) {
		this.companyDto = companyDto;
	}
	
	

}
