package com.Socialblog.AppAuth.Utilites;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private String secret;
    private String expiration;


    private Environment environment;

    @Autowired
    public JwtTokenProvider(Environment environment) {
        this.environment = environment;
        this.secret = environment.getProperty("app-secret");
        this.expiration = environment.getProperty("app-expiration");
    }

    public String generateToken(Authentication authentication)
    {
        String userName = authentication.getName();
        Date current = new Date();
        Date expiration = new Date(current.getTime()+Integer.parseInt(this.expiration));
        String token = Jwts.builder().
                issuer(userName).
                issuedAt(current).
                expiration(expiration).signWith(getKey()).compact();
        return token;
    }
    private Key getKey()
    {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
    public String getUsername(String token)
    {
        //Claims body = Jwts.parser().setSigningKey(getKey()).build().parseClaimsJwt(token).getBody();
       try {
           Claims claims = Jwts.parser()
                   .setSigningKey(getKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
           return claims.getIssuer();
       }
       catch (Exception e)
       {
           System.out.println(e);
       }
       return null;
    }
    public  boolean isValid(String token)
    {
        boolean flag = false;
        try {
            Jwts.parser().setSigningKey(getKey()).build().parse(token);
            flag = true;
        }
        catch (Exception e)
        {

        }
        return flag;
    }
}
