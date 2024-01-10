package com.Socialblog.AppAuth.Utilites;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    CustomUserService customUserService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
       String token = getToken(request.getHeader("Authorization"));
       if(jwtTokenProvider.isValid(token))
       {
            String username = jwtTokenProvider.getUsername(token);
            UserDetails userDetails = customUserService.loadUserByUsername(username);

           UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                   new UsernamePasswordAuthenticationToken(userDetails,null ,userDetails.getAuthorities());

           usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

           SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
       }
        filterChain.doFilter(request,response);
    }

    private String getToken(String authenticationHeader)
    {
        if(authenticationHeader != null && authenticationHeader.startsWith("Bearer "))
            return authenticationHeader.substring(7);
        return "";
    }
}
