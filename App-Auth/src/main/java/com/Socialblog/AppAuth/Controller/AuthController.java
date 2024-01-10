package com.Socialblog.AppAuth.Controller;

import com.Socialblog.AppAuth.Requests.LoginRequest;
import com.Socialblog.AppAuth.Requests.RegisterRequest;
import com.Socialblog.AppAuth.Responses.LoginResponse;
import com.Socialblog.AppAuth.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest)
    {
        LoginResponse loginResponse = authService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping(value = {"signup","register"})
    ResponseEntity<String> register( @RequestBody RegisterRequest registerRequest)
    {
        String result = authService.register(registerRequest);
        return ResponseEntity.ok(result);
    }
    @GetMapping("logout")
    ResponseEntity<String> logout( )
    {

        return ResponseEntity.ok("Logged Out");
    }

}
