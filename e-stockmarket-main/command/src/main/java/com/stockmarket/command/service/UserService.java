package com.stockmarket.command.service;

import com.stockmarket.command.dto.UserDto;
import com.stockmarket.command.entity.Users;
import com.stockmarket.command.mapper.SimpleSourceDestinationMapper;
import com.stockmarket.command.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	@Autowired
	IUserRepository userRepo;

	@Autowired
	SimpleSourceDestinationMapper mapper;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private KafkaService kafkaService;

	public void setKafkaService(KafkaService kafkaService) {
		this.kafkaService = kafkaService;
	}


	@Transactional
	public UserDto createOrUpdateUser(UserDto user)
	{
		LOGGER.info("Creating User");
		UserDto response = new UserDto();
		try
		{
			Users savedUser = userRepo.findByEmail(user.getEmail());
			if (savedUser != null) {
				savedUser.setPhoneNumber(user.getPhoneNumber());
				savedUser.setPassword(passwordEncoder.encode(user.getPassword()));
				savedUser.setUserName(user.getUserName());
				savedUser.setRoles(user.getRoles());
				response = mapper.convertToUserDto(userRepo.save(savedUser));
			}
			else
			{
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				response = mapper.convertToUserDto(userRepo.save(mapper.convertToUser(user)));
				kafkaService.createUserEvent(response);
			}
		}
		catch (Exception e)
		{
			LOGGER.error("Error saving User {}", e.getMessage());
		}
		return response;
	}

	@Transactional
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
				kafkaService.deleteUserEvent(userDto);
				result = true;
			}
		}
		catch (Exception e)
		{
			LOGGER.error("Error deleting User {}", e.getMessage());
		}
		return result;
	}

}
