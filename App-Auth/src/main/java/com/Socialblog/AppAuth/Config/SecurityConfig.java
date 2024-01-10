package com.Socialblog.AppAuth.Config;

import com.Socialblog.AppAuth.Utilites.JwtAuthenticationEntryPoint;
import com.Socialblog.AppAuth.Utilites.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.net.http.HttpRequest;

@Configuration
public class SecurityConfig {

    private JwtAuthenticationEntryPoint authenticationEntryPoint;
    private JwtTokenFilter jwtTokenFilter;
    @Autowired
    public SecurityConfig(JwtAuthenticationEntryPoint authenticationEntryPoint,JwtTokenFilter jwtTokenFilter) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtTokenFilter = jwtTokenFilter;

    }

    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf((AbstractHttpConfigurer::disable));

        httpSecurity.authorizeHttpRequests((request)->request.requestMatchers("api/auth/logout")
                        .authenticated()
                        .anyRequest().permitAll()   ).exceptionHandling((exceptionHandlingConfigurer)->
                exceptionHandlingConfigurer.authenticationEntryPoint(authenticationEntryPoint)).
                sessionManagement((session)->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
