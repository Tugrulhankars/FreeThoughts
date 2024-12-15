package com.tugrulhan.freethoughts.services;

import com.tugrulhan.freethoughts.models.User;
import com.tugrulhan.freethoughts.services.impl.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static javax.crypto.Cipher.SECRET_KEY;

@Service
public class JwtServiceImpl implements JwtService {
    private String signingKey = "f76c9276bf92546381315aa9edbcbb7b30705a4539fd6daa0292c48046aa3790";
    @Override
    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(),userDetails);
    }

    @Override
    public String generateToken(String email,String name) {
        return Jwts.builder()
                .setSubject(email)
                .claim("name", name) // Ek bilgiler eklenebilir
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 saat geçerli
                .signWith(SignatureAlgorithm.HS256, String.valueOf(SECRET_KEY))
                .compact();
    }


    private String generateToken(Map<String, Object> extractCliams,String email){

        return  Jwts.builder().setClaims(extractCliams)
                .setSubject(email)
                .setIssuedAt(new java.util.Date(System.currentTimeMillis()))
                .setExpiration(new java.util.Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();

    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName=extractUsername(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private String generateToken(Map<String, Object> extractCliams,UserDetails userDetails){

        return  Jwts.builder().setClaims(extractCliams)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new java.util.Date(System.currentTimeMillis()))
                .setExpiration(new java.util.Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();

    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new java.util.Date());
    }
    private java.util.Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(signingKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
