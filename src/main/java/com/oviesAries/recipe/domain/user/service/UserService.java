package com.oviesAries.recipe.domain.user.service;

import com.oviesAries.recipe.domain.entity.User;

public interface UserService {
    User createUser(String userName, String password);

    User getUserByUserName(String userName);

    void deleteUserById(Long id);

    User getUserById(Long id);
}
