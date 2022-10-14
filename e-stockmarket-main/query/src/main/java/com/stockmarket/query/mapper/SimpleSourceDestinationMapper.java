package com.stockmarket.query.mapper;

import com.stockmarket.query.dto.CompanyDto;
import com.stockmarket.query.dto.StockDto;
import com.stockmarket.query.dto.UserDto;
import com.stockmarket.query.entity.Company;
import com.stockmarket.query.entity.Stock;
import com.stockmarket.query.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SimpleSourceDestinationMapper {
    CompanyDto convertToCompanyDto(Company company);

    List<CompanyDto> convertToCompanyDtoList(List<Company> companyList);

    Company convertToCompany(CompanyDto companyDto);

    List<Company> convertToCompanyList(List<CompanyDto> companyDtoList);

    StockDto convertToStockDto(Stock stock);

    List<StockDto> convertToStockDtoList(List<Stock> stockList);

    Stock convertToStock(StockDto stockDto);

    List<Stock> convertToStockList(List<StockDto> stockDtoList);

    UserDto convertToUserDto(User user);

    List<UserDto> convertToUserDtoList(List<User> userList);

    User convertToUser(UserDto userDto);

    List<User> convertToUserList(List<UserDto> userDtoList);

}
