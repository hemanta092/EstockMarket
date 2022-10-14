package com.stockmarket.command.controller;

import com.stockmarket.command.dto.CompanyDto;
import com.stockmarket.command.dto.response.CompanyResponse;
import com.stockmarket.command.service.CompanyService;
import com.stockmarket.command.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1.0/market/command/company")
@CrossOrigin(origins="*")
public class CompanyController 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);
	@Autowired
	private CompanyService companyService;

	@Autowired
	private UserService userService;

	@PostMapping(value = "/register")
	public ResponseEntity<CompanyResponse> registerCompany(@RequestBody CompanyDto companyDto) {
		LOGGER.info("Inside register company controller");
		CompanyResponse response = new CompanyResponse();
		
		CompanyDto savedCompany = companyService.createCompany(companyDto);
		if(savedCompany != null)
		{
			response.setIsSuccessful(true);
			response.setCompanyDto(savedCompany);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		LOGGER.info("Exiting register company controller");
		return new ResponseEntity<>(response,HttpStatus.NOT_IMPLEMENTED);
	}
	
	
	@DeleteMapping(value = "/delete/{companyCode}")
	public ResponseEntity<CompanyResponse> deleteCompany(@PathVariable("companyCode") String companyCode)
	{
		LOGGER.info("Inside delete company controller");
		CompanyResponse response = new CompanyResponse();
		boolean isSuccess = companyService.removeCompnay(companyCode);
		if(isSuccess) {
		response.setIsSuccessful(isSuccess);
		return new ResponseEntity<>(response,HttpStatus.OK);
		}
		LOGGER.info("Exiting delete company controller");
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}


}
