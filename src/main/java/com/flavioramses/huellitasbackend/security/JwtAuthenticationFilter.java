package com.flavioramses.huellitasbackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = getTokenFromRequest(request);

            if (token != null) {
                try {
                    if (jwtTokenProvider.validateToken(token)) {
                        Claims claims = jwtTokenProvider.getClaimsFromToken(token);
                        String email = claims.getSubject();
                        String roles = (String) claims.get("roles");

                        List<SimpleGrantedAuthority> authorities = Arrays.stream(roles.split(","))
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList());

                        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } catch (ExpiredJwtException e) {
                    log.error("Token JWT expirado: {}", e.getMessage());
                } catch (UnsupportedJwtException | MalformedJwtException | SignatureException e) {
                    log.error("Token JWT no válido: {}", e.getMessage());
                } catch (Exception e) {
                    log.error("Error al procesar token JWT: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("Error en el filtro de autenticación JWT: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
