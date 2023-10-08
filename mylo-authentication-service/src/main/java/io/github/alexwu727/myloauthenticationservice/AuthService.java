package io.github.alexwu727.myloauthenticationservice;

import io.github.alexwu727.myloauthenticationservice.user.Role;
import io.github.alexwu727.myloauthenticationservice.user.User;
import io.github.alexwu727.myloauthenticationservice.user.UserRepository;
import io.github.alexwu727.myloauthenticationservice.util.JwtUtil;
import io.github.alexwu727.myloauthenticationservice.vo.AuthResponse;
import io.github.alexwu727.myloauthenticationservice.vo.LoginRequest;
import io.github.alexwu727.myloauthenticationservice.vo.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String token = jwtUtil.generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtUtil.generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

}
