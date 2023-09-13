package com.oviesAries.recipe.domain.user.controller;

import com.oviesAries.recipe.domain.entity.User;
import com.oviesAries.recipe.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@WithMockUser
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void 생성() throws Exception {
        //given
        User user = User.builder()
                .userName("testUser")
                .password("testPassword")
                .build();

        //when
        when(userService.createUser("testUser", "testPassword")).thenReturn(user);

        //then
        mockMvc.perform(post("/api/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"user_name\":\"testUser\",\"password\":\"testPassword\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.user_name", is("testUser")))
                .andExpect(jsonPath("$.password", is("testPassword")));
    }

    @Test
    void id로_가져오기() throws Exception{
        //given
        User user = User.builder()
                .id(1L)
                .userName("testUser")
                .password("testPassword")
                .build();
        //when
        when(userService.getUserById(1L)).thenReturn(user);

        //then
        mockMvc.perform(get("/api/users/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.user_name", is("testUser")))
                .andExpect(jsonPath("$.password", is("testPassword")));



    }

    @Test
    void delete() throws Exception{
        //given
        User user = User.builder()
                .id(1L)
                .userName("testUser")
                .password("testPassword")
                .build();
        //when
        doNothing().when(userService).deleteUser(1L);

        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"user_name\":\"testUser\",\"password\":\"testPassword\"}"))
                .andExpect(status().isNoContent());
    }
}