package io.github.alexwu727.mylouserservice;

import io.github.alexwu727.mylouserservice.service.UserService;
import io.github.alexwu727.mylouserservice.util.UserMapper;
import io.github.alexwu727.mylouserservice.vo.UserRegistration;
import io.github.alexwu727.mylouserservice.vo.UserResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        this.userMapper = new UserMapper();
    }

    @GetMapping("/")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = userService.findAll();
        List<UserResponse> userResponses = users.stream().map(userMapper::UserToUserResponse).toList();
        return ResponseEntity.ok(userResponses);
    }

    @PostMapping("/")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegistration userRegistration) {
        User user = userMapper.UserRegistrationToUser(userRegistration);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(user));
    }

    // get user by id
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        UserResponse userResponse = userMapper.UserToUserResponse(user);
        return ResponseEntity.ok(userResponse);
    }
    // get user by username
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable("username") String username) {
        User user = userService.findByUsername(username);
        UserResponse userResponse = userMapper.UserToUserResponse(user);
        return ResponseEntity.ok(userResponse);
    }
}
