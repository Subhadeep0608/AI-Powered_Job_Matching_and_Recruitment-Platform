package com.deep.job_portal.Service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.deep.job_portal.Model.Job;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service 
public class JwtService {

    private String secretKey;

    public JwtService() {
        secretKey = genrateSecretKey();
    }

    private String genrateSecretKey() {
        try{
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretKey = keyGen.generateKey();
            System.out.println("Generated Secret Key: " + secretKey);
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());


        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException("Error generating secret key", e);
        }
    }

    public String generateToken(String username) {

        Map<String,Object> Claims= new HashMap<>();

        return Jwts.builder()
                .setClaims(Claims)
                .setSubject(username)
                .setIssuedAt(new Date (System.currentTimeMillis()))
                .setExpiration(new Date (System.currentTimeMillis() + 1000*5*60))
                .signWith(getKey(),SignatureAlgorithm.HS256).compact();
    }

    private Key getKey() {
        byte [] keyBytes = Base64.getDecoder().decode(secretKey);        
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims Claims = extractAllClaims(token);
        return claimResolver.apply(Claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build().parseClaimsJws(token).getBody();
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    
}
