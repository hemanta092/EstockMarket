package com.stockmarket.query.controller;

import com.stockmarket.query.dto.CompanyDto;
import com.stockmarket.query.service.CompanyService;
import com.stockmarket.query.dto.response.CompanyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1.0/market/query/company")
public class CompanyController 
{

	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);

	@Autowired
	private CompanyService companyService;
	
	@Operation(description = "Get Company using companycode")
	@ApiResponse(responseCode = "200",description = "found company")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "Found company", 
			    content = { @Content(mediaType = "application/json", 
			      schema = @Schema(implementation = CompanyDto.class)) }),
			  @ApiResponse(responseCode = "404", description = "Company not found", 
			    content = @Content) })
	@GetMapping(value = "/info/{companyCode}")
	public ResponseEntity<CompanyResponse> fetchCompanyDetails(@PathVariable("companyCode") String companyCode)
	{
		LOGGER.info("Inside fetch company details controller");

		CompanyResponse response = new CompanyResponse();
		
		CompanyDto savedCompany = companyService.getCompanyByCode(companyCode);
		
		if(savedCompany != null)
		{
			response.setIsSuccessful(true);
			response.setCompanyDto(savedCompany);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		LOGGER.info("Exiting fetch company details controller");
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/getAllNames")
	public ResponseEntity<Map<String,String>> fetchAllCompanyNames()
	{
		LOGGER.info("Inside fetch all company names controller");

		LOGGER.info("Exiting fetch all company names controller");
		return new ResponseEntity<>(new HashMap<>(companyService.getCompanyNames()),HttpStatus.OK);
	}
	
	
	
}
