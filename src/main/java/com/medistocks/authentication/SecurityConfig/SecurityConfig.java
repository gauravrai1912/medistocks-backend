package com.medistocks.authentication.SecurityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/signup").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/forgotPassword").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/resetPassword").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/changePassword").permitAll()
                        .requestMatchers(HttpMethod.POST, "/products").permitAll()
                        .requestMatchers(HttpMethod.GET, "/products").permitAll() 
                        .requestMatchers(HttpMethod.GET, "/products/{id}").permitAll() 
                        .requestMatchers(HttpMethod.PUT, "/products/{id}").permitAll() 
                        .requestMatchers(HttpMethod.DELETE, "/products/{id}").permitAll() 
                        .requestMatchers(HttpMethod.GET, "/suppliers").permitAll()
                        .requestMatchers(HttpMethod.GET, "/suppliers/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/suppliers").permitAll() 
                        .requestMatchers(HttpMethod.PUT, "/suppliers/{id}").permitAll() 
                        .requestMatchers(HttpMethod.DELETE, "/suppliers/{id}").permitAll() 
                        .requestMatchers(HttpMethod.GET, "/inventory").permitAll()
                        .requestMatchers(HttpMethod.GET, "/inventory/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/inventory").permitAll() 
                        .requestMatchers(HttpMethod.GET, "/inventory/check").permitAll() 
                        .requestMatchers(HttpMethod.GET, "/inventory/check2").permitAll() 
                        .requestMatchers(HttpMethod.PUT, "/inventory/{id}").permitAll() 
                        .requestMatchers(HttpMethod.DELETE, "/inventory/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/orders").permitAll()
                        .requestMatchers(HttpMethod.GET, "/orders/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/orders").permitAll() 
                        .requestMatchers(HttpMethod.PUT, "/orders/{id}").permitAll() 
                        .requestMatchers(HttpMethod.DELETE, "/orders/{id}").permitAll()  
                        .requestMatchers(HttpMethod.GET, "/order-details").permitAll()
                        .requestMatchers(HttpMethod.GET, "/order-details/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/order-details").permitAll() 
                        .requestMatchers(HttpMethod.PUT, "/order-details/{id}").permitAll() 
                        .requestMatchers(HttpMethod.DELETE, "/order-details/{id}").permitAll() 
                        .anyRequest().authenticated()
                );
        return httpSecurity.build();
    }
}
