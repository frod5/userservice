package com.example.userservice.controller;

import com.example.userservice.mapper.UserMapper;
import com.example.userservice.vo.RequestUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import com.example.userservice.service.UserService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/")
public class UserController {

    private final Environment env;

    private final UserService userService;

    private final UserMapper userMapper;
    @GetMapping("/health_check")
    public String status() {
        return "It's Working in User Service";
    }
    @GetMapping("/welcome")
    public String welcome() {
        return env.getProperty("greeting.message");
    }

    @PostMapping("/users")
    public String createUser(@RequestBody RequestUser user) {

        userService.createUser(userMapper.toDto(user));
        return "Create user method is called";
    }
}

