package io.github.alexwu727.mylouserservice;

import io.github.alexwu727.mylouserservice.exception.UserNotFoundException;
import io.github.alexwu727.mylouserservice.service.UserService;
import io.github.alexwu727.mylouserservice.util.UserMapper;
import io.github.alexwu727.mylouserservice.vo.UserRegistration;
import io.github.alexwu727.mylouserservice.vo.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    private User user;
    private UserResponse userResponse;
    private UserRegistration userRegistration;

    @BeforeEach
    void setup() {
        user = new User(1L, "alex", "123456", "alex@example.com", new Date());
        userResponse = new UserResponse(1L, "alex", "alex@example.com");
        userRegistration = new UserRegistration("alex", "123456", "alex@example.com");
    }

    @Test
    void getAllUsers_ReturnsAllUsers() {
        // Arrange
        when(userService.findAll()).thenReturn(List.of(user));

        // Act
        ResponseEntity<List<UserResponse>> result = userController.getAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(List.of(userResponse), result.getBody());
    }

    @Test
    void register_WithValidUserRegistration_ReturnsCreatedUser() {
        // Arrange
        when(userService.register(any(User.class))).thenReturn(user);

        // Act
        ResponseEntity<User> result = userController.register(userRegistration);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(user, result.getBody());
    }

    @Test
    void getUserById_WithExistingId_ReturnsUser() {
        // Arrange
        when(userService.findById(1L)).thenReturn(user);

        // Act
        ResponseEntity<UserResponse> result = userController.getUserById(1L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(userResponse, result.getBody());
    }

    @Test
    void getUserById_WithNonExistingId_ThrowsUserNotFoundException() {
        // Arrange
        when(userService.findById(1L)).thenThrow(new UserNotFoundException("User not found"));

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userController.getUserById(1L));
    }

    @Test
    void getUserByUsername_WithExistingUsername_ReturnsUser() {
        // Arrange
        when(userService.findByUsername("alex")).thenReturn(user);

        // Act
        ResponseEntity<UserResponse> result = userController.getUserByUsername("alex");

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(userResponse, result.getBody());
    }

    @Test
    void getUserByUsername_WithNonExistingUsername_ThrowsUserNotFoundException() {
        // Arrange
        when(userService.findByUsername("alex")).thenThrow(new UserNotFoundException("User not found"));

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userController.getUserByUsername("alex"));
    }

    @Test
    void updateUser_WithExistingId_ReturnsUpdatedUser() {
        // Arrange
        when(userService.update(any(Long.class), any(User.class))).thenReturn(user);

        // Act
        ResponseEntity<UserResponse> result = userController.updateUser(1L, userRegistration);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(userResponse, result.getBody());
    }

    @Test
    void updateUser_WithNonExistingId_ThrowsUserNotFoundException() {
        // Arrange
        when(userService.update(any(Long.class), any(User.class))).thenThrow(new UserNotFoundException("User not found"));

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userController.updateUser(1L, userRegistration));
    }

    @Test
    void deleteUser_WithExistingId_ReturnsDeletedUser() {
        // Arrange
        when(userService.findById(1L)).thenReturn(user);

        // Act
        ResponseEntity<UserResponse> result = userController.deleteUser(1L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(userResponse, result.getBody());
    }

    @Test
    void deleteUser_WithNonExistingId_ThrowsUserNotFoundException() {
        // Arrange
        when(userService.findById(1L)).thenThrow(new UserNotFoundException("User not found"));

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userController.deleteUser(1L));
    }
}