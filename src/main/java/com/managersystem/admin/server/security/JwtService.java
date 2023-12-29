package com.managersystem.admin.server.security;

import com.managersystem.admin.ModelMapper;
import com.managersystem.admin.server.entities.AccountEntity;
import com.managersystem.admin.server.pojo.TokenInfo;
import com.managersystem.admin.server.utils.Helper;
import com.managersystem.admin.server.utils.JsonParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Component
public class JwtService {
  @Autowired
  private ModelMapper modelMapper;
  public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
  public String generateToken(AccountEntity account) {
    TokenInfo tokenInfo = modelMapper.toTokenInfo(account);
    Map<String, Object> claims = Helper.convertObjectToMap(tokenInfo);
    return createToken(claims, String.valueOf(account.getId()));
  }

  private String createToken(Map<String, Object> claims, String id) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(id)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
        .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
  }

  private Key getSignKey() {
    byte[] keyBytes= Decoders.BASE64.decode(SECRET);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String extractUserId(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public String extractUsername(String token) {
    final Claims claims = extractAllClaims(token);
    return claims.get("username", String.class);
  }


  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }


}

