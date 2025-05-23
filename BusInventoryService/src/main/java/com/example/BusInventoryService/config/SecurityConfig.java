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
                                .requestMatchers("/busBooking/buses/available/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/busBooking/buses/addBus").hasRole("ADMIN")// Users can query availability
                                .requestMatchers("/busBooking/buses/totalSeats").hasAnyRole("ADMIN","USER")
                                .requestMatchers("/busBooking/buses/addCity").hasRole("ADMIN")
                                .requestMatchers("/busBooking/buses/addRoute").hasRole("ADMIN")
                                .requestMatchers("/busBooking/buses/getBusById").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/busBooking/buses/addStop").hasRole("ADMIN")
                                .requestMatchers("/busBooking/buses/addSchedule").hasRole("ADMIN")
                                .requestMatchers("/busBooking/buses/getCity").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/busBooking/buses/addSeatLock").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/busBooking/buses/getSchedule").hasAnyRole("ADMIN", "USER")
                                .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
                return http.build();
    }
}