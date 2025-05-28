package com.restoreit.services;

import com.restoreit.dtos.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

import static javax.crypto.Cipher.SECRET_KEY;

@Service
public class JWTService {

    private static final String SECRET_KEY = "ReStoreIt2025AuthorizationK3YForBusinessUser";
    private static final long EXPIRATION_TIME = 18000000; // 5 hours

    SecretKey generateKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDTO user) {
        return Jwts.builder()
                .setSubject(user.name)
                .claim("userId", user.id)
                .claim("role", "ROLE_BUSINESS")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(generateKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token, String userId) {
        return userId.equals(extractClaims(token).get("userId")) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

}
