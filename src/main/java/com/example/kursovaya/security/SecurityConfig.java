
package com.example.kursovaya.security;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // Просмотр товаров доступен всем ролям
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/products",
                                "/api/products/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "MANAGER",
                                "CUSTOMER",
                                "USER"
                        )

                        // Добавление товаров
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/products",
                                "/api/products/**"
                        ).hasAnyRole("ADMIN", "MANAGER")

                        // Изменение товаров
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/products",
                                "/api/products/**"
                        ).hasAnyRole("ADMIN", "MANAGER")

                        // Удаление товаров только администратором
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/products",
                                "/api/products/**"
                        ).hasRole("ADMIN")

                        // Все остальные запросы требуют авторизации
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

