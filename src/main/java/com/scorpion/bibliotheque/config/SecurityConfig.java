package com.scorpion.bibliotheque.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter; // Assurez-vous que ce composant est bien déclaré

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     http.csrf(csrf -> csrf.disable())
    //         .authorizeHttpRequests(auth -> auth
    //             .requestMatchers("/api/client/register", "/api/client/login", "/api/client/logout", "/api/client/me", "/api/livres/*").permitAll()
    //             .anyRequest().authenticated()
    //         )
    //         .addFilterBefore(jwtAuthenticationFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

    //     return http.build();
    // }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()  // Toutes les requêtes sont autorisées sans authentification
            )
            .addFilterBefore(jwtAuthenticationFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
}


}
