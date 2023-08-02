package io.github.alexwu727.mylouserservice.service;

import io.github.alexwu727.mylouserservice.User;
import io.github.alexwu727.mylouserservice.UserRepository;
import io.github.alexwu727.mylouserservice.exception.UserNotFoundException;
import io.github.alexwu727.mylouserservice.exception.UsernameAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    // list all user
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException("Username " + user.getUsername() + " already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UsernameAlreadyExistsException("Email " + user.getEmail() + " already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User with username " + username + " not found");
        }
        return user.get();
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        return user.get();
    }

    @Override
    public User update(Long id, User user) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException("Username " + user.getUsername() + " already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UsernameAlreadyExistsException("Email " + user.getEmail() + " already exists");
        }
        User userFromDB = userOptional.get();
        userFromDB.setUsername(user.getUsername());
        userFromDB.setEmail(user.getEmail());
        return userRepository.save(userFromDB);
    }

    @Override
    public User patch(Long id, User user) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        User userFromDB = userOptional.get();
        if (user.getUsername() != null) {
            if (userRepository.existsByUsername(user.getUsername())) {
                throw new UsernameAlreadyExistsException("Username " + user.getUsername() + " already exists");
            }
            userFromDB.setUsername(user.getUsername());
        }
        if (user.getEmail() != null) {
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new UsernameAlreadyExistsException("Email " + user.getEmail() + " already exists");
            }
            userFromDB.setEmail(user.getEmail());
        }
        if (user.getPassword() != null) {
            userFromDB.setPassword(user.getPassword());
        }
        return userRepository.save(userFromDB);
    }

    @Override
    public void delete(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
    }
}
