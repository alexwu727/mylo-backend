package io.github.alexwu727.mylouserservice.service;

import io.github.alexwu727.mylouserservice.User;
import io.github.alexwu727.mylouserservice.UserRepository;
import io.github.alexwu727.mylouserservice.exception.EmailAlreadyExistsException;
import io.github.alexwu727.mylouserservice.exception.UserNotFoundException;
import io.github.alexwu727.mylouserservice.exception.UsernameAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User(1L, "alex", "123456", "alex@example.com", new Date());
        user2 = new User(2L, "bob", "123456", "bob@example.com", new Date());
    }


    @Test
    void findAll_WithTwoUsers_ReturnsTwoUsers() {
        // Arrange
        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = userService.findAll();

        // Assert
        assertEquals(users, result);
    }

    @Test
    void register_WithValidDetails_ReturnsRegisteredUser() {
        // Arrange
        when(userRepository.existsByUsername(user1.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(user1.getEmail())).thenReturn(false);
        when(userRepository.save(user1)).thenReturn(user1);

        // Act
        User result = userService.register(user1);

        // Assert
        assertEquals(user1, result);
    }

    @Test
    void register_WithExistingUsername_ThrowsUsernameAlreadyExistsException() {
        // Arrange
        when(userRepository.existsByUsername(user1.getUsername())).thenReturn(true);

        // Act & Assert
        assertThrows(UsernameAlreadyExistsException.class, () -> userService.register(user1));
    }

    @Test
    void register_WithExistingEmail_ThrowsEmailAlreadyExistsException() {
        // Arrange
        when(userRepository.existsByUsername(user1.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(user1.getEmail())).thenReturn(true);

        // Act & Assert
        assertThrows(EmailAlreadyExistsException.class, () -> userService.register(user1));
    }

    @Test
    void findByUsername_WithExistingUsername_ReturnsUser() {
        // Arrange
        when(userRepository.findByUsername(user1.getUsername())).thenReturn(Optional.of(user1));

        // Act
        User result = userService.findByUsername(user1.getUsername());

        // Assert
        assertEquals(user1, result);
    }

    @Test
    void findByUsername_WithNonExistingUsername_ThrowsUserNotFoundException() {
        // Arrange
        when(userRepository.findByUsername(user1.getUsername())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.findByUsername(user1.getUsername()));
    }

    @Test
    void findById_WithExistingId_ReturnsUser(){
        // Arrange
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));

        // Act
        User result = userService.findById(user1.getId());

        // Assert
        assertEquals(user1, result);
    }

    @Test
    void findById_WithNonExistingId_ThrowsUserNotFoundException() {
        // Arrange
        when(userRepository.findById(user1.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.findById(user1.getId()));
    }

    @Test
    void update_WithValidDetails_ReturnsUpdatedUser() {
        // Arrange
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        when(userRepository.existsByUsername(user1.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(user1.getEmail())).thenReturn(false);
        when(userRepository.save(user1)).thenReturn(user1);

        // Act
        User result = userService.update(user1.getId(), user1);

        // Assert
        assertEquals(user1, result);
    }

    @Test
    void update_WithNonExistingId_ThrowsUserNotFoundException() {
        // Arrange
        when(userRepository.findById(user1.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.update(user1.getId(), user1));
    }

    @Test
    void update_WithExistingUsername_ThrowsUsernameAlreadyExistsException() {
        // Arrange
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        when(userRepository.existsByUsername(user1.getUsername())).thenReturn(true);

        // Act & Assert
        assertThrows(UsernameAlreadyExistsException.class, () -> userService.update(user1.getId(), user1));
    }

    @Test
    void update_WithExistingEmail_ThrowsEmailAlreadyExistsException() {
        // Arrange
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        when(userRepository.existsByEmail(user1.getEmail())).thenReturn(true);

        // Act & Assert
        assertThrows(EmailAlreadyExistsException.class, () -> userService.update(user1.getId(), user1));
    }

    @Test
    void delete_WithExistingId_DeletesUser() {
        // Arrange
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));

        // Act
        userService.delete(user1.getId());

        // Assert
        verify(userRepository, times(1)).deleteById(user1.getId());
    }

    @Test
    void delete_WithNonExistingId_ThrowsUserNotFoundException() {
        // Arrange
        when(userRepository.findById(user1.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.delete(user1.getId()));
    }
}