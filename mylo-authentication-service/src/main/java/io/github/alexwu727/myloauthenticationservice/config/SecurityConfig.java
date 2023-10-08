package io.github.alexwu727.myloauthenticationservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                // cors allow origin http://localhost:3000
                .authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers("/api/v1/greetings/").permitAll()
                                .requestMatchers("/api/v1/greetings/goodbye").permitAll()
                                .requestMatchers("/api/v1/greetings/protected").authenticated()
                                .requestMatchers("/api/v1/greetings/admin").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
