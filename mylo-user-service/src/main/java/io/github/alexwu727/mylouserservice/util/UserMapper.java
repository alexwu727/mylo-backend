package io.github.alexwu727.mylouserservice.util;

import io.github.alexwu727.mylouserservice.User;
import io.github.alexwu727.mylouserservice.vo.UserPatch;
import io.github.alexwu727.mylouserservice.vo.UserRegistration;
import io.github.alexwu727.mylouserservice.vo.UserResponse;

public class UserMapper {
    public User UserRegistrationToUser(UserRegistration userRegistration) {
        User user = new User();
        user.setUsername(userRegistration.getUsername());
        user.setEmail(userRegistration.getEmail());
        user.setPassword(userRegistration.getPassword());
        return user;
    }

    public UserResponse UserToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        return userResponse;
    }

    public User UserPatchToUser(UserPatch userPatch) {
        User user = new User();
        user.setUsername(userPatch.getUsername());
        user.setEmail(userPatch.getEmail());
        user.setPassword(userPatch.getPassword());
        return user;
    }
}
