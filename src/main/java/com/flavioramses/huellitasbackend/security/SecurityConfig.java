package com.flavioramses.huellitasbackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Autenticación
                        .requestMatchers("/api/auth/**").permitAll()
                        
                        // Mantener solo el endpoint de test/status
                        .requestMatchers("/test/status").permitAll()
                        
                        // Rutas públicas para búsqueda y consulta
                        .requestMatchers("/busqueda/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/alojamientos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/categorias/**").permitAll()
                        
                        // Rutas de favoritos
                        .requestMatchers("/favoritos/**").permitAll()
                        
                        // Rutas de perfil
                        .requestMatchers("/perfil/**").permitAll()
                        
                        // Swagger / OpenAPI endpoints
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        
                        // Rutas de reservas
                        .requestMatchers(HttpMethod.GET, "/reservas/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/reservas/**").permitAll() 
                        .requestMatchers(HttpMethod.PUT, "/reservas/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/reservas/**").permitAll()
                        
                        // Rutas de mascotas
                        .requestMatchers("/api/mascotas/**").permitAll()
                        
                        // Rutas de clientes
                        .requestMatchers("/clientes/**").permitAll()
                        
                        // Rutas de usuarios
                        .requestMatchers("/usuarios/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/usuarios/{usuarioId}/rol/{role}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                        
                        // Todo lo demás requiere autenticación
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Orígenes permitidos específicos para credenciales
        configuration.setAllowedOrigins(Arrays.asList(
            "https://grupo-4-proyecto-integrador-dh-frontend-1ep1.vercel.app", 
            "http://localhost:5173",
            "http://localhost:3000",
            "https://rare-compassion-production.up.railway.app"
        ));
        
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList(
                "Origin", 
                "Content-Type", 
                "Accept", 
                "Authorization", 
                "X-Requested-With", 
                "Access-Control-Request-Method", 
                "Access-Control-Request-Headers"
        ));
        configuration.setExposedHeaders(Arrays.asList(
                "Access-Control-Allow-Origin", 
                "Access-Control-Allow-Credentials", 
                "Authorization"
        ));
        
        // Permitir credenciales para que el token se procese correctamente
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        return new JwtAuthenticationFilter(jwtTokenProvider);
    }

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}