package com.stockmarket.command.service;

import com.stockmarket.command.dto.CompanyDto;
import com.stockmarket.command.entity.Company;
import com.stockmarket.command.exception.CompanyExistsException;
import com.stockmarket.command.exception.CompanyTurnOverException;
import com.stockmarket.command.exception.MissingFieldsException;
import com.stockmarket.command.mapper.SimpleSourceDestinationMapper;
import com.stockmarket.command.repository.ICompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CompanyServiceTest
{
    private  final CompanyService companyService = new CompanyService();

    private final ICompanyRepository companyRepository = Mockito.mock(ICompanyRepository.class);
    private final StockService stockService = Mockito.mock(StockService.class);
    private final SimpleSourceDestinationMapper mapper = Mockito.mock(SimpleSourceDestinationMapper.class);
    private final KafkaService kafkaService = Mockito.mock(KafkaService.class);

    @BeforeEach
    public void setup()
    {
        companyService.setCompanyRepo(companyRepository);
        companyService.setStockService(stockService);
        companyService.setMapper(mapper);
        companyService.setKafkaService(kafkaService);
    }
    @Test
    public void testCreateCompany()
    {
        when(companyRepository.findByCompanyCode(any())).thenReturn(null);
        when(mapper.convertToCompanyDto(any())).thenReturn(createCompanyDtoWithAllFields());
        CompanyDto dto = companyService.createCompany(createCompanyDtoWithAllFields());
        assertNotNull(dto);

    }

    @Test
    void testCreateCompanyWhenCompanyExists() {
        when(companyRepository.findByCompanyCode(any())).thenReturn(new Company());
        assertThrows(CompanyExistsException.class, () -> companyService.createCompany(createCompanyDtoWithTurnOver()));
    }

    @Test
    void testCreateCompanyWithoutCompanyTurnover () {
        when(companyRepository.findByCompanyCode(any())).thenReturn(null);
        CompanyDto companyDto = new CompanyDto();
        companyDto.setCompanyTurnOver(12443d);
        assertThrows(CompanyTurnOverException.class, () -> companyService.createCompany(companyDto));
    }

    @Test
    void testCreateCompanyWhenFieldsMissing() {
        assertThrows(MissingFieldsException.class, () -> companyService.createCompany(createCompanyDtoWithTurnOver()));
    }

    @Test
    void testRemoveCompany()
    {
        when(companyRepository.findByCompanyCode(any())).thenReturn(new Company());
        assertTrue(companyService.removeCompnay("ABC"));
    }

    private CompanyDto createCompanyDtoWithTurnOver()
    {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setCompanyTurnOver(200000000000000d);
        return companyDto;
    }

    private CompanyDto createCompanyDtoWithAllFields()
    {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setCompanyCode("ABC");
        companyDto.setCompanyName("ABC Compnay");
        companyDto.setCompanyCEO("ABC CEO");
        companyDto.setCompanyWebsite("www.ABC.com");
        companyDto.setStockExchange("XYZ");
        companyDto.setCompanyTurnOver(200000000000000d);
        return companyDto;
    }
}