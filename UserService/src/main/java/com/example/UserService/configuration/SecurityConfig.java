package com.example.UserService.configuration;

import com.example.UserService.jwt.AccessDenied;
import com.example.UserService.jwt.AuthEntryPoint;
import com.example.UserService.jwt.JwtAuthenticationFilter;
import com.example.UserService.jwt.JwtUtil;
import com.example.UserService.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final AuthEntryPoint authEntryPoint;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final AccessDenied accessDenied;

    public SecurityConfig(AuthEntryPoint authEntryPoint, JwtUtil jwtUtil, UserService userService, AccessDenied accessDenied)
    {
        this.authEntryPoint = authEntryPoint;
        this.jwtUtil=jwtUtil;
        this.userService=userService;
        this.accessDenied = accessDenied;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       http.csrf(AbstractHttpConfigurer::disable);
       http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .authorizeHttpRequests(auth->auth.
                       requestMatchers("/busBooking/users/register").permitAll().
                       requestMatchers("/busBooking/users/login").permitAll()
                       .requestMatchers("/busBooking/users/getByEmail/**").hasAuthority("ROLE_ADMIN")
                       .requestMatchers("/busBooking/users/getAll").hasAuthority("ROLE_ADMIN")
                       .requestMatchers("/busBooking /users/getById/**").hasAuthority("ROLE_ADMIN")
                       .requestMatchers("/busBooking/users/pagination").hasAuthority("ROLE_ADMIN")
                       .anyRequest().authenticated());
                http.addFilterBefore(new JwtAuthenticationFilter(jwtUtil,userService), UsernamePasswordAuthenticationFilter.class)
                        .exceptionHandling(m->m.authenticationEntryPoint(authEntryPoint).accessDeniedHandler(accessDenied));
    return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
