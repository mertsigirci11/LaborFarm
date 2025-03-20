package com.laborfarm.api_gateway.config;

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
public class SecurityConfig{

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth

                        // Auth service
                        .requestMatchers("/auth/login", "/auth/register", "/auth/register/admin").permitAll()

                        .requestMatchers("/auth/role", "/auth/role/{id}").hasAnyAuthority("PROJECT_GROUP_MANAGER", "PROJECT_MANAGER", "DEVELOPER")

                        // Core app
                        .requestMatchers("/api/departments", "/api/projects", "/api/users").hasAuthority("PROJECT_GROUP_MANAGER")

                        .requestMatchers("/api/projectmembers").hasAnyAuthority("PROJECT_GROUP_MANAGER", "PROJECT_MANAGER")

                        .requestMatchers("/api/fileinfos", "/api/taskcomments", "/api/tasks").authenticated()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}