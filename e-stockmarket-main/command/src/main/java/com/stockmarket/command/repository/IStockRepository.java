package com.stockmarket.command.repository;

import com.stockmarket.command.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IStockRepository extends JpaRepository<Stock,Long>
{
    Stock findByCompanyCode(String companyCode);
}
