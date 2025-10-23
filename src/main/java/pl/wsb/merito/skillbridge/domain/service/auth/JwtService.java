package pl.wsb.merito.skillbridge.domain.service.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    @Value("${spring.security.sha-key}")
    private String SHA_KEY;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(SHA_KEY));
    }

    public String generateToken(String userId, String username) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("username", username)
                .setIssuedAt(new Date())
                .signWith(getSecretKey())
                .compact();
    }
}