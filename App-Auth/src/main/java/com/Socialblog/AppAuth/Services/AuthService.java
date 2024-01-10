package com.Socialblog.AppAuth.Services;


import com.Socialblog.AppAuth.Entities.User;
import com.Socialblog.AppAuth.Repository.UserRepository;
import com.Socialblog.AppAuth.Requests.LoginRequest;
import com.Socialblog.AppAuth.Requests.RegisterRequest;

import com.Socialblog.AppAuth.Responses.LoginResponse;

import com.Socialblog.AppAuth.Utilites.JwtTokenProvider;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;



@Service
public class AuthService {

    private AuthenticationManager authenticationManager;
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager,
                       ModelMapper mapper,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.modelMapper = mapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public LoginResponse login(LoginRequest loginRequest)
    {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));

        String token  = jwtTokenProvider.generateToken(authentication);
        return new LoginResponse(token);
    }
    public String register(RegisterRequest registerRequest)
    {

        User user = modelMapper.map(registerRequest,User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user);
        userRepository.save(user);
        return "Registered successfully";
    }
    User maptouser(RegisterRequest registerRequest)
    {
        return null;
    }
}
