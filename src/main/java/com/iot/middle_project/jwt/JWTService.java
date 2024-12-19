package com.iot.middle_project.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.jwt-expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.refresh-jwt-expiration}")
    private long refreshExpiration;

//    --------------------------------------------------KEY--------------------------------------------------

    private SecretKey getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);    //chuyen key dang BASE64 sang dinh dang chuoi bits
        return Keys.hmacShaKeyFor(keyBytes);                    //bam thanh 256 bits
    }

    //    --------------------------------------------------GEN TOKEN--------------------------------------------------
    public String generateToken(Map<String, Object> claims,
                                String username,
                                long expiration){
        return Jwts
                .builder()                                       // Trả về một instance của builder
                .claims(claims)
                .subject(username)                               // Thiết lập subject (thuong la primary key)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey())
                .compact();                                       // Trả về JWT token đã hoàn thiện

    }

//    public String generateToken(Map<String, Object> claims, String username){
//        return generateToken(claims, username, jwtExpiration);
//    }

    public String generateToken(String username){
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("type", "access");
        return generateToken(claims, username, jwtExpiration);
    }

    public String generateRefreshToken(String username){
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh");
        return generateToken(claims, username, refreshExpiration);
    }

    //    --------------------------------------------------EXTRACT CLAIMS--------------------------------------------------
    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()                                            // Xây dựng JwtParser
                .parseSignedClaims(token)                           // Phân tích token
                .getPayload();                                      // Lấy phần payload (Claims) của token
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsTFunction){
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);                       // .apply truyen argument
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public String extractType(String token){
        return extractClaim(token, claims -> claims.get("type", String.class));
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    //    --------------------------------------------------VALID--------------------------------------------------
    public boolean validToken(String token, UserDetails userDetails){
        String usernameToken = extractUsername(token);
        return ( usernameToken.equals(userDetails.getUsername()) && !isTokenExpired(token) );
//        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
}
