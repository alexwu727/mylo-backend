package io.github.alexwu727.mylouserservice.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
<<<<<<< HEAD
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
=======
import lombok.Data;

@Data
>>>>>>> user service - get users, get user by id, get user by username, and register
public class UserRegistration {
    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email is not valid")
    private String email;
}
