package com.example.UserService.exception;


import com.example.UserService.jwt.AccessDenied;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler {


       @ExceptionHandler(Exception.class)
       public ProblemDetail handleException(Exception e)
       {
           ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
           problemDetail.setTitle("INTERNAL_SERVER_ERROR");
           problemDetail.setDetail(e.getMessage());
           problemDetail.setType(URI.create("https://example.com/errors/INTERNAL_SERVER_ERROR"));
           return problemDetail;
       }
}
