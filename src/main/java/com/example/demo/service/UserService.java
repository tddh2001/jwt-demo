package com.example.demo.service;

import com.example.demo.model.db.User;
import com.example.demo.model.request.AuthRequest;
import com.example.demo.model.response.UserResponse;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.example.demo.constants.Constants.USER_ADD_SUCCESS;
import static com.example.demo.constants.Constants.USER_NOT_FOUND;

@Component
public class UserService{

    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, JWTService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse createUser(User user) {
        UserResponse response = new UserResponse();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        response.setMessage(USER_ADD_SUCCESS);
        return response;
    }

    public String login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(request.getUsername());
        } else {
            throw new UsernameNotFoundException(USER_NOT_FOUND);
        }
    }
}
