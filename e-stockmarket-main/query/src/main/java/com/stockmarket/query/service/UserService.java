package com.stockmarket.query.service;

import com.stockmarket.query.dto.UserDto;
import com.stockmarket.query.entity.User;
import com.stockmarket.query.mapper.SimpleSourceDestinationMapper;
import com.stockmarket.query.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;


@Service
public class UserService  {

	public static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	@Autowired
	IUserRepository userRepo;

	@Autowired
	SimpleSourceDestinationMapper mapper;


	public UserDto createOrUpdateUser(UserDto user) {
		LOGGER.info("Creating User");
		UserDto response = new UserDto();
		try {
			User savedUser = userRepo.findByEmail(user.getEmail());
			if (savedUser != null) {
				savedUser.setPhoneNumber(user.getPhoneNumber());
				savedUser.setPassword(user.getPassword());
				savedUser.setUserName(user.getUserName());
				savedUser.setRoles(user.getRoles());
				response = mapper.convertToUserDto(userRepo.save(savedUser));
			}
			else
			{
				user.setPassword(user.getPassword());
				response = mapper.convertToUserDto(userRepo.save(mapper.convertToUser(user)));
			}
		} catch (Exception e) {
			LOGGER.error("Error saving User {}", e.getMessage());
		}
		return response;
	}
	

	public boolean deleteUser(UserDto userDto) {
		boolean result = false;
		LOGGER.info("Authenticationg the user");
		if(userDto.getUserName()!=null && userDto.getPassword()!=null)
		{
			LOGGER.error("User Name Or Password cannot be null");
		}
		try
		{
			if(userRepo.existsById(userDto.getId()))
			{
				userRepo.delete(mapper.convertToUser(userDto));
				result = true;
			}
		}
		catch (Exception e)
		{
			LOGGER.error("Error deleting User {}", e.getMessage());
		}
		return result;
	}

	public UserDto authenticateUser(UserDto userDto)
	{
		boolean result = false;
		LOGGER.info("Authenticationg the user");

		if(userDto.getEmail()==null || userDto.getPassword()==null)
		{
			LOGGER.error("Email Or Password cannot be null");
			return null;
		}
		User user = userRepo.findByEmail(userDto.getEmail());

		if(user!=null)
		{
			result = BCrypt.checkpw(userDto.getPassword(),user.getPassword());
		}

		return result?mapper.convertToUserDto(user):null;

	}

}
