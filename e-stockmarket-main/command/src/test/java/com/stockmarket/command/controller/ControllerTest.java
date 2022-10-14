package com.stockmarket.command.controller;

import com.stockmarket.command.dto.CompanyDto;
import com.stockmarket.command.dto.StockDto;
import com.stockmarket.command.entity.Stock;
import com.stockmarket.command.repository.IStockRepository;
import com.stockmarket.command.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ControllerTest extends AbstractTest
{
    private final StockService stockService = Mockito.mock(StockService.class);
    private final IStockRepository stockRepository = Mockito.mock(IStockRepository.class);


    @BeforeEach
    public void setUp() {

        super.setUp();
        stockService.setStockRepo(stockRepository);
    }
    @Test
    public void createCompany() throws Exception {
        String uri = "/api/v1.0/market/command/company/register";
        CompanyDto company = new CompanyDto();
        company.setId(1L);
        company.setCompanyName("COMPANY_NAME");
        company.setCompanyCode("CODE");
        company.setCompanyCEO("COMPANY_CEO");
        company.setCompanyTurnOver(20000000000d);
        company.setCompanyWebsite("www.company.in");
        company.setStockExchange("SE");
        String inputJson = super.mapToJson(company);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertNotNull(content);
    }

    @Test
    public void deleteCompany() throws Exception {
        String uri = "/api/v1.0/market/command/company/delete/CODE";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertNotNull(content);
    }

    @Test
    void registerStock() throws Exception {
        String uri = "/api/v1.0/market/command/stock/registerstock";
        StockDto stock = new StockDto();
        stock.setId(1L);
        stock.setCompanyCode("COMPANY_CODE");
        stock.setCreatedOn(new Date());
        stock.setStockPrice(20000000000d);
        stock.setUpdatedOn(new Date());
        String inputJson = super.mapToJson(stock);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertNotNull(content);
    }

}