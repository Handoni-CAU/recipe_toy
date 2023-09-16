package com.oviesAries.recipe.domain.user.dao;

import com.oviesAries.recipe.domain.entity.User;
import com.oviesAries.recipe.domain.user.domain.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserName(String userName);

    Optional<User> findById(Long id);

    void deleteById(Long id);
    boolean existsByUserName(String userName);

    boolean existsById(Long id);

    Optional<User> findByEmail(final Email email);
}
