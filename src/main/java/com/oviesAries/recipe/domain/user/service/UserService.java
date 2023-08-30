package com.oviesAries.recipe.domain.user.service;

import com.oviesAries.recipe.domain.entity.User;

public interface UserService {
    public User createUser(String username, String password);

    public User getUser(String username);

    public User updateUser(String username, String password);

    public void deleteUser(String username);
}
