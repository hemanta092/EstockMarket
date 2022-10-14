package com.stockmarket.query.controller;

import com.stockmarket.query.dto.UserDto;
import com.stockmarket.query.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0/market/query/user")
public class UserController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping(value = "/authenticateuser")
    public ResponseEntity<UserDto> authenticateUser(@RequestBody UserDto userDto)
    {
        LOGGER.info("Inside authenticate user controller");

        UserDto result = userService.authenticateUser(userDto);

        if(result!=null)
        {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        LOGGER.info("Exiting authenticate user controller");

        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }
}
