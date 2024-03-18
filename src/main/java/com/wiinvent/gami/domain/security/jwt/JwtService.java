package com.wiinvent.gami.domain.security.jwt;

import com.wiinvent.gami.ModelMapper;
import com.wiinvent.gami.domain.entities.Account;
import com.wiinvent.gami.domain.pojo.TokenInfo;
import com.wiinvent.gami.domain.utils.Helper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
@Log4j2
public class JwtService {
  @Autowired private ModelMapper modelMapper;
  @Value(value = "${jwt.secret}")
  public String secret;
  @Value(value = "${jwt.expire_time}")
  public Long expireTime;
  public String generateToken(Account account) {
    TokenInfo tokenInfo = modelMapper.toTokenInfo(account);
    Map<String, Object> claims = Helper.convertObjectToMap(tokenInfo);
    return createToken(claims, String.valueOf(account.getId()));
  }

  private String createToken(Map<String, Object> claims, String id) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(id)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expireTime))
        .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
  }

  private Key getSignKey() {
    byte[] keyBytes= Decoders.BASE64.decode(secret);
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

  public boolean validateToken(String authToken) {
    try {
      Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(authToken);
      return true;
    } catch (MalformedJwtException ex) {
      log.debug("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      log.debug("Expired JWT token");
    } catch (UnsupportedJwtException ex) {
      log.debug("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      log.debug("JWT claims string is empty.");
    }
    return false;
  }



}

