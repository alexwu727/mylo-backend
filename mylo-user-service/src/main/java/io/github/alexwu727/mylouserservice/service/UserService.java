package io.github.alexwu727.mylouserservice.service;

import io.github.alexwu727.mylouserservice.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User register(User user);
    User findByUsername(String username);
    User findById(Long id);
    User update(Long id, User user);
<<<<<<< HEAD
    User patch(Long id, User user);
=======
>>>>>>> user service - get users, get user by id, get user by username, and register
    void delete(Long id);
}
