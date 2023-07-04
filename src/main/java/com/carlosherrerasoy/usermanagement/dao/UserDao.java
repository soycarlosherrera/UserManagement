package com.carlosherrerasoy.usermanagement.dao;

import com.carlosherrerasoy.usermanagement.models.User;

import java.util.List;

public interface UserDao {

    List<User> getUsers();
    void deletUser(Long id);
    void registerUsers(User user);
    User getUserByCredentials(User user);
}
