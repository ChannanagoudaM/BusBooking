package com.example.UserService.jwt;

import com.example.UserService.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserService userService)
    {
        this.jwtUtil=jwtUtil;
        this.userService=userService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
         //here authorization begins
        String header=request.getHeader(HttpHeaders.AUTHORIZATION);
        if(header!=null && header.startsWith("Bearer "))
        {
            String token=header.substring(7);
            if(jwtUtil.validateToken(token))
            {
                String email = jwtUtil.extractMailFromToken(token);
                log.info("Subject from token {}", email);
                UserDetails userDetails = userService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
