package com.stockmarket.query.repository;

import com.stockmarket.query.entity.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ICompanyRepository extends MongoRepository<Company,String>
{
	Company findByCompanyCode(String companyCode);
	Company findByCompanyName(String companyName);

}
