package com.stockmarket.command.service;

import com.stockmarket.command.dto.StockDto;
import com.stockmarket.command.entity.Stock;
import com.stockmarket.command.mapper.SimpleSourceDestinationMapper;
import com.stockmarket.command.repository.ICompanyRepository;
import com.stockmarket.command.repository.IStockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
public class StockService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StockService.class);

	@Autowired
	private IStockRepository stockRepo;
	
	@Autowired
	private ICompanyRepository companyRepo;

	@Autowired
	private KafkaService kafkaService;

	@Autowired
	private SimpleSourceDestinationMapper mapper;

	public void setStockRepo(IStockRepository stockRepo) {
		this.stockRepo = stockRepo;
	}

	public void setCompanyRepo(ICompanyRepository companyRepo) {
		this.companyRepo = companyRepo;
	}

	public void setKafkaService(KafkaService kafkaService) {
		this.kafkaService = kafkaService;
	}

	public void setMapper(SimpleSourceDestinationMapper mapper) {
		this.mapper = mapper;
	}


	@Transactional
	public StockDto createStock(StockDto stockDto) {
		LOGGER.info("Creating Stock");
		StockDto response = new StockDto();
		try {
			response = mapper.convertToStockDto(stockRepo.save(mapper.convertToStock(stockDto)));
			kafkaService.createStockEvent(response);
		} catch (Exception e) {
			LOGGER.error("Error saving Stock {}", e.getMessage());
		}
		return response;
	}

	@Transactional
	public Boolean deleteStock(Long id) {
		boolean isSuccessfull = false;
		Optional<Stock> stock = stockRepo.findById(id);
		try {
			if (stock.isPresent()) 
			{
				stockRepo.deleteById(id);
				kafkaService.deleteStockEvent(mapper.convertToStockDto(stock.get()));
				isSuccessfull = true;
			}
		} catch (Exception e) {
			LOGGER.error("Error deleting Stock with id-{} {}", id, e.getMessage());
		}
		return isSuccessfull;
	}

	@Transactional
	public Boolean deleteStockByCompanyCode(String companyCode) {
		boolean isSuccessfull = false;
		Stock stock = stockRepo.findByCompanyCode(companyCode);
		try {
			if (stock!=null)
			{
				stockRepo.deleteById(stock.getId());
				kafkaService.deleteStockEvent(mapper.convertToStockDto(stock));
				isSuccessfull = true;
			}
		} catch (Exception e) {
			LOGGER.error("Error deleting Stock with id-{} {}", stock.getId(), e.getMessage());
		}
		return isSuccessfull;
	}


}
