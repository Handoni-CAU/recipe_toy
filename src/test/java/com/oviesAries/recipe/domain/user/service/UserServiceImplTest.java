package com.oviesAries.recipe.domain.user.service;

import com.oviesAries.recipe.domain.entity.User;
import com.oviesAries.recipe.domain.user.dao.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void createUser() {
        //given
        User user = User.builder()
                .userName("testUser")
                .password("testPassword")
                .build();
        //when
        when(userRepository.save(any(User.class))).thenReturn(user);
        //then
        User result = userService.createUser("testUser", "testPassword");
        assertNotNull(result);
        assertEquals("testUser", result.getUserName());
        assertEquals("testPassword", result.getPassword());
    }

    @Test
    void getUserByUserName() {
        //given
        User user = User.builder()
                .userName("testUser")
                .password("testPassword")
                .build();
        //when
        when(userRepository.findByUserName("testUser")).thenReturn(java.util.Optional.of(user));
        //then
        User result = userService.getUserByUserName("testUser");
        assertNotNull(result);
        assertEquals("testUser", result.getUserName());
        assertEquals("testPassword", result.getPassword());
    }

    @Test
    void deleteUser() {
        //given
        User user = User.builder()
                .id(1L)
                .build();
        doNothing().when(userRepository).deleteById(1L);

        //when
        userService.deleteUserById(1L);

        //then
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void getUserById() {
        //given
        User user = User.builder()
                .id(1L)
                .userName("testUser")
                .password("testPassword")
                .build();
        //when
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        //then
        User result = userService.getUserById(1L);
        assertNotNull(result);
        assertEquals(Long.valueOf(1L), result.getId());
        assertEquals("testUser", result.getUserName());
        assertEquals("testPassword", result.getPassword());
    }
}