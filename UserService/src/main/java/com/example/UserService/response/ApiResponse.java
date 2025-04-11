package com.example.UserService.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T>{

    private String message;
    private HttpStatusCode httpStatusCode;
    private T object;
}
