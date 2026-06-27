package com.example.projeto2bi_fciii.security;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JWTTokenProvider
{
    private static final String DEFAULT_JWT_SECRET = "development-only-jwt-secret-change-me-32bytes";

    private static SecretKey getSigningKey()
    {
        return Keys.hmacShaKeyFor(getJwtSecret().getBytes(StandardCharsets.UTF_8));
    }

    private static String getJwtSecret()
    {
        String propertyValue = System.getProperty("ativooperante.jwt.secret");
        if(propertyValue != null && !propertyValue.isBlank())
            return propertyValue;

        String envValue = System.getenv("APP_JWT_SECRET");
        if(envValue != null && !envValue.isBlank())
            return envValue;

        return DEFAULT_JWT_SECRET;
    }

    static public String createToken(String usuario, String nivel)
    {       
        String jwtToken = Jwts.builder()
            .setSubject(usuario)
            .setIssuer("localhost:8080")
            .claim("nivel", nivel)
            .setIssuedAt(new Date())
            .setExpiration(Date.from(LocalDateTime.now().plusMinutes(15L)
                .atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(getSigningKey())
            .compact();
        return jwtToken;        
    }

    static public boolean verifyToken(String token)
    {
        try
        {
            Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token).getSignature();
                return true;
       }
        catch (Exception e)
        {
            System.out.println(e);
        }
       return false;       
    }

    static public Claims getAllClaimsFromToken(String token) 
    {
        Claims claims=null;
        try
        {
            claims = Jwts.parser()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
        }
        catch (Exception e)
        {
            System.out.println("Erro ao recuperar as informações (claims)");
        }
        return claims;        
    }

}
