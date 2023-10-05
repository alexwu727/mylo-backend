package io.github.alexwu727.mylouserservice.service;

import io.github.alexwu727.mylouserservice.User;
import io.github.alexwu727.mylouserservice.UserRepository;
<<<<<<< HEAD
import io.github.alexwu727.mylouserservice.exception.EmailAlreadyExistsException;
=======
>>>>>>> user service - get users, get user by id, get user by username, and register
import io.github.alexwu727.mylouserservice.exception.UserNotFoundException;
import io.github.alexwu727.mylouserservice.exception.UsernameAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

<<<<<<< HEAD
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

=======
    @Autowired
    private UserRepository userRepository;

    // list all user
>>>>>>> user service - get users, get user by id, get user by username, and register
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
<<<<<<< HEAD
            throw new EmailAlreadyExistsException("Email " + user.getEmail() + " already exists");
=======
            throw new UsernameAlreadyExistsException("Email " + user.getEmail() + " already exists");
>>>>>>> user service - get users, get user by id, get user by username, and register
        }
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
<<<<<<< HEAD
        if (user.isEmpty()) {
=======
        if (!user.isPresent()) {
>>>>>>> user service - get users, get user by id, get user by username, and register
            throw new UserNotFoundException("User with username " + username + " not found");
        }
        return user.get();
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
<<<<<<< HEAD
        if (user.isEmpty()) {
=======
        if (!user.isPresent()) {
>>>>>>> user service - get users, get user by id, get user by username, and register
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        return user.get();
    }

    @Override
    public User update(Long id, User user) {
<<<<<<< HEAD
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException("Username " + user.getUsername() + " already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("Email " + user.getEmail() + " already exists");
        }
        User userFromDB = userOptional.get();
        userFromDB.setUsername(user.getUsername());
        userFromDB.setEmail(user.getEmail());
        return userRepository.save(userFromDB);
    }

    @Override
    public User patch(Long id, User user) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
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
=======
        return null;
>>>>>>> user service - get users, get user by id, get user by username, and register
    }

    @Override
    public void delete(Long id) {
<<<<<<< HEAD
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        userRepository.deleteById(id);
=======

>>>>>>> user service - get users, get user by id, get user by username, and register
    }
}
