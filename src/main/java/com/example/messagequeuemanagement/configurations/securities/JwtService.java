package com.example.messagequeuemanagement.configurations.securities;

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
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {
    private static final String SECRET_KEY = "dR8VhF5zy0R2p8Pjft1U8Zjkexkny35IOUcdMC0rfy0LsVvC0qj94BklHs7py0YBnt8L4bN2IqYlVWksDGMJvg==";
    public String extractUserName(String jwt) {
        return extractClaim(jwt,Claims::getSubject);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        String token = Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ (1000*36000*24)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
        return token;
    }
    public String generateTokenRefresh(Long id) {
        return Jwts
                .builder()
                .setSubject(id.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ (10000*60*24)))
                .compact();
    }

    public String generateRefreshToken(Long id){
        return generateTokenRefresh(id);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> roleList = new HashMap<>();
        roleList.put(
                "roles",
                userDetails
                        .getAuthorities()
                        .stream()
                        .map(
                                (role)->{
                                    return role.toString();
                                }
                        ).collect(Collectors.toList())
        );
        return generateToken(roleList,userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extraExpiration(token).before(new Date());
    }

    private Date extraExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);

    }
}
