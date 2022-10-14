package com.stockmarket.command.mapper;

import com.stockmarket.command.entity.Users;
import com.stockmarket.command.dto.CompanyDto;
import com.stockmarket.command.dto.StockDto;
import com.stockmarket.command.dto.UserDto;
import com.stockmarket.command.entity.Company;
import com.stockmarket.command.entity.Stock;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SimpleSourceDestinationMapper
{
    CompanyDto convertToCompanyDto(Company company);
    List<CompanyDto> convertToCompanyDtoList(List<Company> companyList);
    Company convertToCompany(CompanyDto companyDto);
    List<Company> convertToCompanyList(List<CompanyDto> companyDtoList);

    StockDto convertToStockDto(Stock stock);
    List<StockDto> convertToStockDtoList(List<Stock> stockList);
    Stock convertToStock(StockDto stockDto);
    List<Stock> convertToStockList(List<StockDto> stockDtoList);

    UserDto convertToUserDto(Users user);
    List<UserDto> convertToUserDtoList(List<Users> userList);
    Users convertToUser(UserDto userDto);
    List<Users> convertToUserList(List<UserDto> userDtoList);
}
