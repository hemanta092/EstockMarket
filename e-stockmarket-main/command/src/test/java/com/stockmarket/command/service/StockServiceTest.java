package com.stockmarket.command.service;

import com.stockmarket.command.dto.StockDto;
import com.stockmarket.command.entity.Stock;
import com.stockmarket.command.mapper.SimpleSourceDestinationMapper;
import com.stockmarket.command.repository.ICompanyRepository;
import com.stockmarket.command.repository.IStockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class StockServiceTest
{
    private  final StockService stockService = new StockService();

    private final ICompanyRepository companyRepository = Mockito.mock(ICompanyRepository.class);
    private final IStockRepository stockRepository = Mockito.mock(IStockRepository.class);
    private final SimpleSourceDestinationMapper mapper = Mockito.mock(SimpleSourceDestinationMapper.class);
    private final KafkaService kafkaService = Mockito.mock(KafkaService.class);

    @BeforeEach
    public void setup()
    {
        stockService.setCompanyRepo(companyRepository);
        stockService.setStockRepo(stockRepository);
        stockService.setMapper(mapper);
        stockService.setKafkaService(kafkaService);
    }

    @Test
    void testCreateStock()
    {
        when(mapper.convertToStockDto(any())).thenReturn(new StockDto());
        StockDto dto = stockService.createStock(new StockDto());
        assertNotNull(dto);
    }

    @Test
    void testDeleteStock()
    {
        when(stockRepository.findById(any())).thenReturn(Optional.of(new Stock()));
        assertTrue(stockService.deleteStock(1L));
    }

    @Test
    void testDeleteStockByCompanyCode()
    {
        Stock stock = new Stock();
        stock.setId(1L);
        when(stockRepository.findByCompanyCode(any())).thenReturn(stock);
        assertTrue(stockService.deleteStockByCompanyCode("XYZ"));
    }}