package io.github.alexwu727.mylouserservice.unitTest;

import io.github.alexwu727.mylouserservice.Role;
import io.github.alexwu727.mylouserservice.User;
import io.github.alexwu727.mylouserservice.UserRepository;
import io.github.alexwu727.mylouserservice.exception.EmailAlreadyExistsException;
import io.github.alexwu727.mylouserservice.exception.UserNotFoundException;
import io.github.alexwu727.mylouserservice.exception.UsernameAlreadyExistsException;
import io.github.alexwu727.mylouserservice.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestTemplate restTemplate;

    private User user1;
    private User user2;
    private ResponseEntity<Map<String, String>> authResponse;
    private String token;


    @BeforeEach
    void setUp() {
        user1 = new User(1L, "alex", "123456", "alex@example.com", Role.USER, new Date());
        user2 = new User(2L, "bob", "123456", "bob@example.com", Role.ADMIN, new Date());
        token = "token";
        Map<String, String> authResponseMap = new HashMap<>();
        authResponseMap.put("token", token);
        authResponse = new ResponseEntity<>(authResponseMap, null, HttpStatus.OK);
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
        doReturn(authResponse).when(restTemplate).postForEntity(anyString(), any(), any());
        // Act
        Pair<User, String> result = userService.register(user1);

        // Assert
        assertEquals(user1, result.getFirst());
        assertEquals(token, result.getSecond());
    }

    @Test
    void register_WithExistingUsername_ThrowsUsernameAlreadyExistsException() {
        // Arrange
        when(userRepository.existsByUsername(user1.getUsername())).thenReturn(true);

        // Act & Assert
        Exception exception = assertThrows(UsernameAlreadyExistsException.class, () -> userService.register(user1));
        assertEquals("Username " + user1.getUsername() + " already exists", exception.getMessage());
    }

    @Test
    void register_WithExistingEmail_ThrowsEmailAlreadyExistsException() {
        // Arrange
        when(userRepository.existsByUsername(user1.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(user1.getEmail())).thenReturn(true);

        // Act & Assert
        Exception exception = assertThrows(EmailAlreadyExistsException.class, () -> userService.register(user1));
        assertEquals("Email " + user1.getEmail() + " already exists", exception.getMessage());
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
        Exception exception = assertThrows(UserNotFoundException.class, () -> userService.findByUsername(user1.getUsername()));
        assertEquals("User with username " + user1.getUsername() + " not found", exception.getMessage());
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
        Exception exception = assertThrows(UserNotFoundException.class, () -> userService.findById(user1.getId()));
        assertEquals("User with id " + user1.getId() + " not found", exception.getMessage());
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
        Exception exception = assertThrows(UserNotFoundException.class, () -> userService.update(user1.getId(), user1));
        assertEquals("User with id " + user1.getId() + " not found", exception.getMessage());
    }

    @Test
    void update_WithExistingUsername_ThrowsUsernameAlreadyExistsException() {
        // Arrange
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        when(userRepository.existsByUsername(user1.getUsername())).thenReturn(true);

        // Act & Assert
        Exception exception = assertThrows(UsernameAlreadyExistsException.class, () -> userService.update(user1.getId(), user1));
        assertEquals("Username " + user1.getUsername() + " already exists", exception.getMessage());
    }

    @Test
    void update_WithExistingEmail_ThrowsEmailAlreadyExistsException() {
        // Arrange
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        when(userRepository.existsByEmail(user1.getEmail())).thenReturn(true);

        // Act & Assert
        Exception exception = assertThrows(EmailAlreadyExistsException.class, () -> userService.update(user1.getId(), user1));
        assertEquals("Email " + user1.getEmail() + " already exists", exception.getMessage());
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
        Exception exception = assertThrows(UserNotFoundException.class, () -> userService.delete(user1.getId()));
        assertEquals("User with id " + user1.getId() + " not found", exception.getMessage());
    }
}