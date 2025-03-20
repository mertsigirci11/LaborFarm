package com.laborfarm.api_gateway.config;

import com.laborfarm.api_gateway.service.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final JwtUtil jwtUtil;

    @Autowired
    public AuthenticationFilter(JwtUtil jwtUtil) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                if(isAuthorized(exchange.getRequest().getURI().getPath(), Collections.emptyMap()))
                {
                    return chain.filter(exchange);
                }

                return exchange.getResponse().setComplete(); // Terminate the request if token is not provided and path different from login & register
            }

            String token = authHeader.substring(7);

            if (!jwtUtil.isTokenValid(token)) {
                return exchange.getResponse().setComplete(); // Terminate the request if token is not valid
            }

            Map<String, String> roles = jwtUtil.extractRoles(token);
            String userEmail = jwtUtil.extractEmail(token);

            // Authorization check
            String requestPath = exchange.getRequest().getURI().getPath();
            if (!isAuthorized(requestPath, roles)) {
                return exchange.getResponse().setComplete(); // Terminate the request if role is not authorized
            }

            // Send user informations to backend
            exchange.getRequest().mutate()
                    .header("X-User-Email", userEmail)
                    .header("X-User-Roles", String.join(",", roles.values()))
                    .build();

            // Proceed to next process
            return chain.filter(exchange);
        };
    }

    private boolean isAuthorized(String path, Map<String, String> roles) {
        if (path.startsWith("/auth/login") || path.startsWith("/auth/register")) {
            return true;
        }
        if (path.startsWith("/auth/role")) {
            return roles.containsValue("PROJECT_GROUP_MANAGER") || roles.containsValue("PROJECT_MANAGER");
        }
        if (path.startsWith("/api/departments") || path.startsWith("/api/projects") || path.startsWith("/api/users")) {
            return roles.containsValue("PROJECT_GROUP_MANAGER");
        }
        if (path.startsWith("/api/projectmembers")) {
            return roles.containsValue("PROJECT_GROUP_MANAGER") || roles.containsValue("PROJECT_MANAGER");
        }
        if (path.startsWith("/api/fileinfos") || path.startsWith("/api/taskcomments") || path.startsWith("/api/tasks")) {
            return !roles.isEmpty();
        }
        return false;
    }

    public static class Config {

    }
}
