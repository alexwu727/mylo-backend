package io.github.alexwu727.mylouserservice.service;

import io.github.alexwu727.mylouserservice.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User register(User user);
    User findByUsername(String username);
    User findById(Long id);
    User update(Long id, User user);
    User patch(Long id, User user);
    void delete(Long id);
}
