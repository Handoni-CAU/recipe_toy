package com.oviesAries.recipe.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oviesAries.recipe.domain.user.application.AuthService;
import com.oviesAries.recipe.domain.user.dao.UserRepository;
import com.oviesAries.recipe.domain.user.dto.request.LoginRequest;
import com.oviesAries.recipe.domain.user.dto.request.SignUpRequest;
import com.oviesAries.recipe.domain.user.dto.response.LoginResponse;
import com.oviesAries.recipe.domain.user.infra.TokenProvider;
import com.oviesAries.recipe.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@RunWith(SpringRunner.class)
@WithMockUser
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthService authService;

    @MockBean
    private TokenProvider tokenProvider;

    private SignUpRequest signUpRequest;
    private LoginRequest loginRequest;
    @BeforeEach
    public void setUp() {
        signUpRequest = SignUpRequest.builder()
                .userName("testUserName")
                .nickName("testNickName")
                .password("testPassword")
                .email("testemail@email.com")
                .build();
        loginRequest = LoginRequest.builder()
                .email("testemail@email.com")
                .password("testPassword")
                .build();
    }
    @Test
    public void testSignup() throws Exception {
        given(authService.signUp(signUpRequest)).willReturn(1L);

        mockMvc.perform(post("/api/users/signup")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"testUserName\", \"nickName\":\"testNickName\", \"password\":\"testPassword\", \"email\":\"testemail@email.com\"}"))
                .andDo(print())
                .andDo(document("users/signup",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().isCreated());
    }

    @Test
    void login() throws Exception{
        given(authService.loginMember(loginRequest))
                .willReturn(LoginResponse.builder()
                        .accessToken("testAccessToken")
                        .name("testNickName")
                        .email("testemail@email.com")
                        .build());
        mockMvc.perform(post("/api/users/login")
                        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"testemail@email.com\", \"password\":\"testPassword\"}"))
                .andDo(print())
                .andDo(document("users/login",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().isOk());
    }

    @Test
    void createRecipeIngredient() {
        
    }

    @Test
    void getAllIngredient() {
    }

    @Test
    void deleteUserById() {

    }

    @Test
    void deleteIngredient() {
    }
}