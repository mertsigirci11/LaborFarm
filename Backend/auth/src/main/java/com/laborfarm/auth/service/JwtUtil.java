package com.laborfarm.auth.service;

import com.laborfarm.auth.entity.UserLoginInfo;
import com.laborfarm.auth.entity.UserRole;
import com.laborfarm.auth.entity.UserRoleInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

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


    // -------------------------------------- Helper Methods --------------------------------------
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}