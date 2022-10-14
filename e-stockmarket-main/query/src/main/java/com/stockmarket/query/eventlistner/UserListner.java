package com.stockmarket.query.eventlistner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockmarket.query.dto.UserDto;
import com.stockmarket.query.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserListner
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyListner.class);
    @Autowired
    private UserService userService;

    @KafkaListener(topics = "deleteUser", groupId = "group_id")
    public void consumeDelete(String message) throws JsonMappingException, JsonProcessingException {
        LOGGER.info(String.format("$$$ -> Consumed Delete User Message -> %s",message));
        UserDto user = new ObjectMapper().readValue(message, UserDto.class);
        Boolean isUserDeleted = userService.deleteUser(user);
        LOGGER.info("isUserDeleted - ",isUserDeleted);

    }

    @KafkaListener(topics = "createUser", groupId = "group_id")
    public void consumeCreate(String message) throws JsonMappingException, JsonProcessingException{
        LOGGER.info(String.format("$$$ -> Consumed Create User Message -> %s",message));
        UserDto user = new ObjectMapper().readValue(message, UserDto.class);
        UserDto savedUser = userService.createOrUpdateUser(user);
        LOGGER.info("saved User{}",savedUser);

    }
}
