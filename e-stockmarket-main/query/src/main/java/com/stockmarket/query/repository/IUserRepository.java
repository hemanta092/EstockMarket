package com.stockmarket.query.repository;

import com.stockmarket.query.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends MongoRepository<User, Long>
{
    User findByEmail(String email);
}
