package com.example.demo.service;

import com.example.demo.constants.Constants;
import com.example.demo.model.db.User;
import com.example.demo.model.response.UserResponse;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.example.demo.constants.Constants.USER_ADD_SUCCESS;

@Component
public class UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public UserResponse createUser(User user) {
        UserResponse response = new UserResponse();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        response.setMessage(USER_ADD_SUCCESS);
        return response;
    }
}
