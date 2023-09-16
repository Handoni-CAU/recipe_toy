package com.oviesAries.recipe.domain.user.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oviesAries.recipe.domain.entity.User;
import com.oviesAries.recipe.domain.user.dao.UserRepository;
import com.oviesAries.recipe.domain.user.domain.EncodedPassword;
import com.oviesAries.recipe.domain.user.dto.request.SignUpRequest;
import com.oviesAries.recipe.domain.user.infra.TokenProvider;
import com.oviesAries.recipe.domain.user.security.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordValidator passwordValidator;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;

    public Long signUp(final SignUpRequest request) {
        passwordValidator.validatePassword(request.getPassword());

        final User member = User.of(
                request.getUserName(),
                request.getNickName(),
                EncodedPassword.of(request.getPassword(), passwordEncoder),
                request.getEmail()
        );

        return userRepository.save(member).getId();
    }


}
