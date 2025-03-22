package com.laborfarm.core_app.service;

import com.laborfarm.common.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class KafkaConsumerService {

    private final UserService userService;

    @Autowired
    public KafkaConsumerService(UserService userService) {
        this.userService = userService;
    }

    @KafkaListener(topics = "user-saving", groupId = "my-group")
    public void consumeMessage(UserDto userDto) {
        userDto.setCreatedAt(new Date());
        userService.addUser(userDto);
        System.out.println("User saved successfully" + userDto.toString());
    }
}
