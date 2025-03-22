package com.laborfarm.auth.service;

import com.laborfarm.auth.entity.UserLoginInfo;
import com.laborfarm.auth.entity.UserRole;
import com.laborfarm.auth.entity.UserRoleInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtUtil {
    private final static String SECRET_KEY = "YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd4eXoxMjM0NTY3ODkwMTIzNDU2Nzg=";

    // Token Generation
    public String generateToken(UserLoginInfo user, List<UserRoleInfo> roles){
        Map<String, Object> extraClaims = new HashMap<>();

        // ProjectId <-> RoleName info map
        Map<String, String> projectRoleMap = new HashMap<>();
        roles.forEach(role -> {
            String key = (role.getRoleId() == UserRole.PROJECT_GROUP_MANAGER.getValue())
                    ? "SUPER USER"
                    : role.getProjectId().toString();
            projectRoleMap.put(key, UserRole.fromValue(role.getRoleId()).name());
        });

        // Claims info entry
        extraClaims.put("userId", user.getId());
        extraClaims.put("roles", projectRoleMap);

        return Jwts.builder()
                .claims(extraClaims)
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (1000*60*5)))
                .signWith(getSignInKey())
                .compact();
    }

    // Token validation
    public boolean isTokenValid(String token) {
        try {
            boolean isExpired = isTokenExpired(token);
            if (isExpired) {
                return false;
            }

            Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Get email
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Get roles
    public Map<String, String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        Object rolesClaim = claims.get("roles");

        if (rolesClaim instanceof Map) {
            return (Map<String, String>) rolesClaim;
        }
        return Collections.emptyMap();
    }

    // -------------------------------------- Helper Methods --------------------------------------

    // Get All Claims
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Check Token Expiration
    private boolean isTokenExpired(String token) {
        Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration != null && expiration.before(new Date());
    }

    // Get Specific Claim
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}