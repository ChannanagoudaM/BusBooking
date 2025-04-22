package com.example.UserService.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AccessDenied implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        Map<String,Object>map=new HashMap<>();
        map.put("status",HttpServletResponse.SC_FORBIDDEN);
        map.put("error","Forbidden");
        map.put("path",request.getServletPath());
        map.put("message",accessDeniedException.getMessage());

        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(),map);
    }
}
