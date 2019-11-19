package com.chacetech.users.dao;

import com.chacetech.users.model.User;

import java.util.List;

public interface IUserRepository {
    List<User> findByUserName(String userName);
}