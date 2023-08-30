package com.oviesAries.recipe.domain.user.service;

import com.oviesAries.recipe.domain.entity.User;
import com.oviesAries.recipe.domain.user.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User createUser(String username, String password) {
        User user = User.builder()
                .userName(username)
                .password(passwordEncoder.encode(password))
                .build();

        return userRepository.save(user);
    }


    @Override
    public User getUser(String username) {
        return userRepository.findById(username).orElse(null);
    }

    @Override
    public User updateUser(String username, String password) {
        User user = User.builder()
                .userName(username)
                .password(passwordEncoder.encode(password))
                .build();

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }
}
