package com.oviesAries.recipe.domain.user.dao;

import com.oviesAries.recipe.domain.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = User.builder().userName("testUser").password("testPassword").build();

        userRepository.save(testUser);
    }

    @AfterEach
    public void tearDown() {
        userRepository.delete(testUser);
    }

    @Test
    public void findByUserName_ShouldReturnUser() {
        Optional<User> found = userRepository.findByUserName(testUser.getUserName());

        assertThat(found).isPresent();
        assertThat(found.get().getUserName()).isEqualTo(testUser.getUserName());
    }

    @Test
    public void findById_ShouldReturnUser() {
        Optional<User> found = userRepository.findById(testUser.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(testUser.getId());
    }

    @Test
    public void existsByUserName_ShouldReturnTrue() {
        boolean found = userRepository.existsByUserName(testUser.getUserName());

        assertThat(found).isTrue();
    }

    @Test
    public void existsById_ShouldReturnTrue() {
        boolean found = userRepository.existsById(testUser.getId());

        assertThat(found).isTrue();
    }

    @Test
    public void deleteById_ShouldDeleteUser() {
        userRepository.deleteById(testUser.getId());

        boolean found = userRepository.existsById(testUser.getId());

        assertThat(found).isFalse();
    }
}
