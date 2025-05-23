package com.example.UserService.service;

import com.example.UserService.constants.RabbitMQConstants;
import com.example.UserService.entity.User;
import com.example.UserService.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;
    private final AmqpTemplate amqpTemplate;

    public UserService(UserRepository userRepository, AmqpTemplate amqpTemplate)
    {
        this.userRepository=userRepository;
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }

    @Cacheable(cacheNames = "allUsers")
    public List<User> getAll()
    {
        return userRepository.findAll();
    }

    @Cacheable(cacheNames = "getByeEmail")
    public User findByEmail(String email)
    {
        return userRepository.findByEmail(email).get();
    }

    @Cacheable(cacheNames = "getById")
    public User getById(int id)
    {
       log.info("fetching from db");
        return userRepository.findById(id).get();
    }

    @RabbitListener(queues = RabbitMQConstants.USER_REQUEST_QUEUE)
    public User onMessage(int userId) {
        return userRepository.findById(userId).get();
    }
}
