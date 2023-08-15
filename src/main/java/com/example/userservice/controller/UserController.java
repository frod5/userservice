package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user-service")
public class UserController {

    private final Environment env;
    private final UserService userService;
    private final UserMapper userMapper;
    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in User Service on PORT %s", env.getProperty("local.server.port"));
    }
    @GetMapping("/welcome")
    public String welcome() {
        return env.getProperty("greeting.message");
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {

        UserDto dto = userMapper.toDto(user);
        userService.createUser(dto);
        return new ResponseEntity<>(userMapper.toResponseDto(dto), HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {

        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByAll().stream()
                .map(userMapper::entityToDto)
                .map(userMapper::toResponseDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable(name = "userId") String userId) {
        return ResponseEntity.ok(userMapper.toResponseDto(userService.getUserByUserId(userId)));
    }
}

