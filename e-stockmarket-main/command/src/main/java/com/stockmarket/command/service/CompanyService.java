package com.stockmarket.command.service;

import com.stockmarket.command.dto.CompanyDto;
import com.stockmarket.command.entity.Company;
import com.stockmarket.command.exception.CompanyExistsException;
import com.stockmarket.command.exception.CompanyTurnOverException;
import com.stockmarket.command.exception.MissingFieldsException;
import com.stockmarket.command.mapper.SimpleSourceDestinationMapper;
import com.stockmarket.command.repository.ICompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class CompanyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

	@Autowired
	private ICompanyRepository companyRepo;

	@Autowired
	private KafkaService kafkaService;

	@Autowired
	private StockService stockService;

	@Autowired
	private SimpleSourceDestinationMapper mapper;

	public void setCompanyRepo(ICompanyRepository companyRepo) {
		this.companyRepo = companyRepo;
	}

	public void setKafkaService(KafkaService kafkaService) {
		this.kafkaService = kafkaService;
	}

	public void setStockService(StockService stockService) {
		this.stockService = stockService;
	}

	public void setMapper(SimpleSourceDestinationMapper mapper) {
		this.mapper = mapper;
	}

	@Transactional
	public CompanyDto createCompany(CompanyDto companyDto) {
		LOGGER.info("Inside Creating Company service");
		CompanyDto response = new CompanyDto();
		LOGGER.info("Fetching company with company code - {} ",companyDto.getCompanyCode());
		Company company = companyRepo.findByCompanyCode(companyDto.getCompanyCode());
		if (company != null) {
			throw new CompanyExistsException();
		}
		else if (companyDto.getCompanyTurnOver()<(100000000d))
		{
			throw new CompanyTurnOverException();
		}
		else if (!validateCompanyFields(companyDto))
		{
			throw new MissingFieldsException();
		}
		else
		{
			try
			{
				response = mapper.convertToCompanyDto(companyRepo.save(mapper.convertToCompany(companyDto)));
				kafkaService.createCompanyEvent(response);
			}
			catch (Exception e)
			{
				LOGGER.error("Error saving Company {}", e.getMessage());
			}
		}
		LOGGER.info("Exiting Creating Company service");
		return response;

	}

	@Transactional
	public Boolean removeCompnay(String companyCode) {
		boolean isSuccessfull = false;
		Company company = companyRepo.findByCompanyCode(companyCode);

		try {
			stockService.deleteStockByCompanyCode(companyCode);
			companyRepo.delete(company);
			isSuccessfull = true;
			kafkaService.deleteCompanyEvent(mapper.convertToCompanyDto(company));
		} catch (Exception e) {
			LOGGER.error("Error deleting Company with companyCode -{} {}", companyCode, e.getMessage());
		}
		return isSuccessfull;
	}

	
	private boolean validateCompanyFields(CompanyDto companyDto) {
		return stringMandatoryCheck(companyDto.getCompanyCode()) && stringMandatoryCheck(companyDto.getCompanyCEO())
				&& stringMandatoryCheck(companyDto.getCompanyName())
				&& stringMandatoryCheck(companyDto.getCompanyWebsite())
				&& stringMandatoryCheck(companyDto.getStockExchange());

	}

	private boolean stringMandatoryCheck(String value) {
		return value != null && !value.isEmpty();
	}
}
