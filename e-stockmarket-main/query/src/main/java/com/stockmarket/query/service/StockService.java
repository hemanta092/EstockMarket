package com.stockmarket.query.service;

import com.stockmarket.query.dto.AggregateDto;
import com.stockmarket.query.dto.StockDto;
import com.stockmarket.query.entity.Company;
import com.stockmarket.query.entity.Stock;
import com.stockmarket.query.mapper.SimpleSourceDestinationMapper;
import com.stockmarket.query.repository.ICompanyRepository;
import com.stockmarket.query.repository.IStockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class StockService
{

	public static final Logger LOGGER = LoggerFactory.getLogger(StockService.class);

	@Autowired
	private IStockRepository stockRepo;

	@Autowired
	private ICompanyRepository companyRepository;

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private SimpleSourceDestinationMapper mapper;

	public StockDto createStock(StockDto stockDto) {
		LOGGER.info("Creating Stock");
		StockDto response = new StockDto();
		try {
			response = mapper.convertToStockDto(stockRepo.save(mapper.convertToStock(stockDto)));

		} catch (Exception e) {
			LOGGER.error("Error saving Stock {}", e.getMessage());
		}
		return response;
	}

	public Boolean deleteStock(Long id) {
		boolean isSuccessfull = false;
		Optional<Stock> stock = stockRepo.findById(id.toString());
		try {
			if (stock.isPresent())
			{
				stockRepo.deleteById(id.toString());
				isSuccessfull = true;
			}
		} catch (Exception e) {
			LOGGER.error("Error deleting Stock with id-{} {}", id, e.getMessage());
		}
		return isSuccessfull;
	}

	public Boolean deleteStockByCompanyCode(String companyCode) {
		boolean isSuccessfull = false;
		Stock stock = stockRepo.findByCompanyCode(companyCode);
		try {
			if (stock!=null)
			{
				stockRepo.deleteById(stock.getId().toString());
				isSuccessfull = true;
			}
		} catch (Exception e) {
			LOGGER.error("Error deleting Stock with id-{} {}", stock.getId(), e.getMessage());
		}
		return isSuccessfull;
	}


	public List<StockDto> getStocks(String companyCode, Date startDate, Date endDate) {
		Criteria criteria = Criteria.where("companyCode").is(companyCode);
		criteria.andOperator(Criteria.where("updatedOn").gte(startDate).lt(endDate));
		List<Stock> stockList =  mongoOperations.find(Query.query(criteria), Stock.class);
		List<StockDto> result = new ArrayList<>();
		result.addAll(mapper.convertToStockDtoList(stockList));
		return result;
	}
	

	public AggregateDto getAggregateStock(String companyCode, Date startDate, Date endDate) {
		AggregationResults<AggregateDto> result = mongoOperations.aggregate(Aggregation.
				newAggregation(Aggregation.match(Criteria.where("companyCode").is(companyCode).
						andOperator(Criteria.where("updatedOn").gte(startDate).lt(endDate))),
						Aggregation.group("companyCode").first("companyCode").as("companyCode")
						.min("stockPrice").as("minStockPrice").max("stockPrice").as("maxStockPrice")
						.avg("stockPrice").as("avgStockPrice")), Stock.class,AggregateDto.class);
		return result.getUniqueMappedResult();
	}

	public List<String> getCompanyNames()
	{
		return companyRepository.findAll().stream().map(Company::getCompanyName).collect(Collectors.toList());
	}

}
