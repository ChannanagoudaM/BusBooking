package com.example.UserService.controller;

import com.example.UserService.dto.AuthRequest;
import com.example.UserService.dto.AuthResponse;
import com.example.UserService.entity.RefreshTokenEntity;
import com.example.UserService.entity.User;
import com.example.UserService.jwt.JwtUtil;
import com.example.UserService.repository.RefreshTokenRepository;
import com.example.UserService.repository.UserRepository;
import com.example.UserService.response.ApiResponse;
import com.example.UserService.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
@Slf4j
public class AuthController {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    public AuthController(RefreshTokenRepository refreshTokenRepository, JwtUtil jwtUtil, UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager, UserService userService, UserService userService1)
    {
        this.repository=repository;
        this.passwordEncoder=bCryptPasswordEncoder;
        this.authenticationManager=authenticationManager;
        this.jwtUtil=jwtUtil;
        this.refreshTokenRepository=refreshTokenRepository;
        this.userService = userService1;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@RequestBody User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User save = repository.save(user);
        ApiResponse<User>apiResponse=new ApiResponse<>(
                "SUCCESS",
                HttpStatus.CREATED,
                user
        );
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {// here authentication is happening
        authenticationManager.authenticate(// this authentication manager uses loadByUsername()
                //here using username and password authentication will be done
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = repository.findByEmail(request.getEmail()).get();

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        RefreshTokenEntity entity=new RefreshTokenEntity(refreshToken, user.getEmail(), 604800000);
        refreshTokenRepository.save(entity);

        return ResponseEntity.ok(new AuthResponse(token, refreshToken));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAll()
    {
        List<User> all = userService.getAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<User> getByEmail(@PathVariable String email)
    {
        User byEmail = userService.findByEmail(email);
        return ResponseEntity.ok(byEmail);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<User> getById(@PathVariable int id)
    {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<User>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        log.info("entered controller");
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = repository.findAll(pageable);
        return ResponseEntity.ok(userPage);
    }
}
