package com.stockmarket.query.repository;

import com.stockmarket.query.entity.Stock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IStockRepository extends MongoRepository<Stock,String>
{
    Stock findByCompanyCode(String companyCode);
}
