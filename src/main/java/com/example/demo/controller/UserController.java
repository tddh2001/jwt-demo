package com.example.demo.controller;

import com.example.demo.constants.Constants;
import com.example.demo.model.db.User;
import com.example.demo.model.request.AuthRequest;
import com.example.demo.model.request.UserRequest;
import com.example.demo.model.response.UserResponse;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.JWTService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.example.demo.constants.Constants.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, JWTService jwtService, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public UserResponse createUser(@RequestBody UserRequest request) {
        UserResponse response = new UserResponse();
        try {
            request.validate();
        } catch (IllegalArgumentException e) {
            response.setMessage(e.getMessage());
            return response;
        }
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
        if (userOpt.isPresent()) {
            try {
                throw new IllegalAccessException();
            } catch (IllegalAccessException e) {
                response.setMessage(USER_EXISTED);
                return response;
            }
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRoles(request.getRoles());
        response.setMessage(REGISTER_SUCCESSFULLY);
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public UserResponse authenticateAndGetToken(@RequestBody AuthRequest request) {
        UserResponse response = new UserResponse();
        try {
            request.validate();
        } catch (IllegalArgumentException e) {
            response.setMessage(e.getMessage());
            return response;
        }
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            boolean checkPassword = encoder.matches(request.getPassword(), user.getPassword());
            if (checkPassword) {
                String token = userService.login(request);
                response.setMessage(token);
            } else {
                response.setMessage(INCORRECT_PASSWORD);
            }
        } else {
            response.setMessage(USER_NOT_FOUND);
        }
        return response;
    }
}
