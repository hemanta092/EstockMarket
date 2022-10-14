package com.stockmarket.command.controller;

import com.stockmarket.command.dto.UserDto;
import com.stockmarket.command.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1.0/market/command/user")
@CrossOrigin(origins="*")
public class UserController 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;


	@PostMapping(value = "/registeruser")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto)
	{
		LOGGER.info("Inside register user controller");
		UserDto savedUser = userService.createOrUpdateUser(userDto);

		if(savedUser != null)
		{
			return new ResponseEntity<>(savedUser,HttpStatus.OK);
		}
		LOGGER.info("Exiting register user controller");
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	

	
}
