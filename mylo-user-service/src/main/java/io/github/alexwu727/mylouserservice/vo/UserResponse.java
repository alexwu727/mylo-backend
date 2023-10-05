package io.github.alexwu727.mylouserservice.vo;

<<<<<<< HEAD
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
=======
import lombok.Data;

@Data
>>>>>>> user service - get users, get user by id, get user by username, and register
public class UserResponse {
    private Long id;
    private String username;
    private String email;
}
