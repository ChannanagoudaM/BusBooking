package com.example.UserService.controller;

import com.example.UserService.dto.AuthRequest;
import com.example.UserService.dto.AuthResponse;
import com.example.UserService.entity.RefreshTokenEntity;
import com.example.UserService.entity.User;
import com.example.UserService.jwt.JwtUtil;
import com.example.UserService.repository.RefreshTokenRepository;
import com.example.UserService.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class AuthController {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthController(RefreshTokenRepository refreshTokenRepository,JwtUtil jwtUtil,UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder,AuthenticationManager authenticationManager)
    {
        this.repository=repository;
        this.passwordEncoder=bCryptPasswordEncoder;
        this.authenticationManager=authenticationManager;
        this.jwtUtil=jwtUtil;
        this.refreshTokenRepository=refreshTokenRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        return ResponseEntity.ok("registered");
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = repository.findByEmail(request.getEmail()).get();
        System.out.println(user.getPassword());
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        RefreshTokenEntity entity=new RefreshTokenEntity(refreshToken, user.getEmail(), 604800000);
        refreshTokenRepository.save(entity);

        return ResponseEntity.ok(new AuthResponse(token, refreshToken));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAll()
    {
        List<User> all = repository.findAll();
        for(User user:all)
        {
            System.out.println(user.getName());
        }
        return ResponseEntity.ok(all);
    }
}
