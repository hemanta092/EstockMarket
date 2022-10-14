package com.stockmarket.query.service;

import com.stockmarket.query.dto.CompanyDto;
import com.stockmarket.query.entity.Company;
import com.stockmarket.query.mapper.SimpleSourceDestinationMapper;
import com.stockmarket.query.repository.ICompanyRepository;
import com.stockmarket.query.exception.CompanyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CompanyService  {

	public static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

	@Autowired
	private ICompanyRepository companyRepo;

	@Autowired
	private SimpleSourceDestinationMapper mapper;

	@Autowired
	private StockService stockService;


	public CompanyDto createCompany(CompanyDto companyDto) {
		LOGGER.info("Creating Company");
		CompanyDto response = new CompanyDto();
		Company company = companyRepo.findByCompanyCode(companyDto.getCompanyCode());
		if (company != null) {
			throw new CompanyExistsException();
		} else {
			try {
				response = mapper.convertToCompanyDto(companyRepo.save(mapper.convertToCompany(companyDto)));
			} catch (Exception e) {
				LOGGER.error("Error saving Company {}", e.getMessage());
			}
		}
		return response;

	}

	public Boolean removeCompnay(String companyCode) {
		boolean isSuccessfull = false;
		Company company = companyRepo.findByCompanyCode(companyCode);

		try {
			stockService.deleteStockByCompanyCode(companyCode);
			companyRepo.delete(company);
			isSuccessfull = true;
		} catch (Exception e) {
			LOGGER.error("Error deleting Company with companyCode -{} {}", companyCode, e.getMessage());
		}
		return isSuccessfull;
	}

	public Map<String,String> getCompanyNames() {
		return companyRepo.findAll().stream().collect(Collectors.toMap(Company::getCompanyCode,Company::getCompanyName));
	}

	public CompanyDto getCompanyByCode(String companyCode) {
		Company company = companyRepo.findByCompanyCode(companyCode);
		CompanyDto companyDto = new CompanyDto();
		if (company != null) {
			companyDto = mapper.convertToCompanyDto(company);
		}
		return companyDto;
	}

	public String getCompanyCodeByName(String companyName) {
		return companyRepo.findByCompanyName(companyName).getCompanyCode();
	}

}
