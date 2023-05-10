package com.example.demo.config;

import com.example.demo.model.db.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.example.demo.constants.Constants.USER_NOT_FOUND;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOtp = userRepository.findByUsername(username);
        return userOtp.map(UserInfoUserDetails::new)
                .orElseThrow(()->new UsernameNotFoundException(USER_NOT_FOUND+" "+username));
    }
}
