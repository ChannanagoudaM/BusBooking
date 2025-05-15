package com.example.BusInventoryService.config;

import com.example.BusInventoryService.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests->
                        requests
                                .requestMatchers("/buses/available/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/buses/addBus").hasRole("ADMIN")// Users can query availability
                                .requestMatchers("/buses/totalSeats").hasAnyRole("ADMIN","USER")
                                .requestMatchers("/buses/addCity").hasRole("ADMIN")
                                .requestMatchers("/buses/addRoute").hasRole("ADMIN")
                                .requestMatchers("/buses/addStop").hasRole("ADMIN")
                                .requestMatchers("/buses/addSchedule").hasRole("ADMIN")
                                .requestMatchers("/buses/getCity").hasAnyRole("ADMIN","USER")
                                .requestMatchers("/buses/addSeatLock").hasAnyRole("ADMIN","USER")
                                .requestMatchers("/buses/getSchedule").hasAnyRole("ADMIN","USER")
                                .requestMatchers("/buses/**", "/schedules/**").hasRole("ADMIN")
                                .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
                return http.build();
    }
}