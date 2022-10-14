package com.stockmarket.command.repository;

import com.stockmarket.command.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ICompanyRepository extends JpaRepository<Company,String>
{
	Company findByCompanyCode(String companyCode);
}
