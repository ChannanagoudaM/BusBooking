package com.example.BookingService.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;

@Component
public class JwtUtil {

        @Value("${jwt.secrete-key}")
        private String secreteKey;

        @Value("${jwt.expiration}")
        private Long expiration;

        @Value("${jwt.refresh-expiration}")
        private Long refreshExpiration;

        public String extractMailFromToken(String token)
        {
                return Jwts.parserBuilder()
                        .setSigningKey(getKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();
        }

        public String extractRoleFromToken(String token)
        {
               return Jwts.parserBuilder()
                       .setSigningKey(getKey())
                       .build()
                       .parseClaimsJws(token)
                       .getBody()
                       .get("role")
                       .toString();
        }

        public boolean validateToken(String token)
        {
                try
                {
                        Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
                        return true;
                }
                catch(Exception e)
                {
                        return false;
                }
        }


        public Key getKey()
        {
            byte[]decode=Decoders.BASE64.decode(secreteKey);
            return Keys.hmacShaKeyFor(decode);
        }
}
