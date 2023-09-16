package com.oviesAries.recipe.domain.user.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oviesAries.recipe.domain.entity.User;
import com.oviesAries.recipe.domain.user.dao.UserRepository;
import com.oviesAries.recipe.domain.user.domain.AuthPrincipal;
import com.oviesAries.recipe.domain.user.domain.Email;
import com.oviesAries.recipe.domain.user.domain.EncodedPassword;
import com.oviesAries.recipe.domain.user.dto.request.LoginRequest;
import com.oviesAries.recipe.domain.user.dto.request.SignUpRequest;
import com.oviesAries.recipe.domain.user.dto.response.LoginResponse;
import com.oviesAries.recipe.domain.user.exception.LoginFailException;
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

    @Transactional(readOnly = true)
    public LoginResponse loginMember(final LoginRequest loginRequest) {
        User user = getMemberByEmail(loginRequest.getEmail());
        user.validatePassword(loginRequest.getPassword(), passwordEncoder);
        AuthPrincipal authPrincipal = AuthPrincipal.from(user);

        String accessToken = tokenProvider.createToken(principalToJson(authPrincipal));
        return LoginResponse.of(accessToken, user.getNickName(), user.getEmail());
    }

    private String principalToJson(final AuthPrincipal authPrincipal) {
        try {
            return objectMapper.writeValueAsString(authPrincipal);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }


    private User getMemberByEmail(final String email) {
        return userRepository.findByEmail(Email.from(email))
                .orElseThrow(LoginFailException::new);
    }


}
