package com.flavioramses.huellitasbackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class JwtTokenProvider {

    // Clave secreta fija para evitar que los tokens se invaliden cuando se reinicia el servicio
    private static final String SECRET_KEY = "huellitasSecretKeyParaElTokenJwtEsteEsUnaClaveLargaYSegura2024";
    private Key jwtSecret;
    private long jwtExpirationInMs = 3600000; // 1 Hora

    public JwtTokenProvider() {
        // Convertir la cadena en una clave compatible con el algoritmo
        byte[] keyBytes = Base64.getEncoder().encode(SECRET_KEY.getBytes());
        this.jwtSecret = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", roles) // Include roles in the token
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(jwtSecret, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getEmailFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    public Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new RuntimeException("Token inválido o expirado", e);
        }
    }

    private boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}