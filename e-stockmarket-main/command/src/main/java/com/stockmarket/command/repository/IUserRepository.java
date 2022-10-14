package com.stockmarket.command.repository;

import com.stockmarket.command.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<Users, Long>
{
	Users findByEmail(String email);

	Users findByUserName(String userName);
}
