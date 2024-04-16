package com.project.resiRed.service;

import com.project.resiRed.entity.RefreshToken;
import com.project.resiRed.entity.User;
import com.project.resiRed.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String getToken(User user) { // Aqui tambien seria necesario cambiar por User
        return getToken(new HashMap<>(), user);
    }

    public String getToken(Map<String, Object> extraClaims, User user) { // para obtener claims personalizados de la clase User reemplazamos UserDetails por la clase User creada manualmente
        return Jwts
                .builder()
                .claims(extraClaims)
                .claim("username", user.getFirstName())
                .claim("lastname", user.getLastname())
                .claim("address", user.getAddress())
                .claim("role", user.getRole())
                .claim("type", "ACCESS_TOKEN")
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) // el token de acceso cambia cada 10 mins
                .signWith(getKey())
                .compact();
    }

    /***/
    public String generateRefreshToken(User user) {
        String refreshToken = Jwts.builder()
                .claim("type", "REFRESH_TOKEN")
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 3)) // 3 horas de sesión activa
                .signWith(getKey())
                .compact();

        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUser(user);
        if (existingToken.isPresent()) {
            // existingToken.get().setExpirationDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 3));
            return existingToken.get().getToken();
        } else {
            RefreshToken newToken = new RefreshToken();
            newToken.setToken(refreshToken);
            newToken.setUser(user);
            newToken.setExpirationDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 3));
            refreshTokenRepository.save(newToken);
        }

        return refreshToken;
    }

    public boolean isAccessToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return "ACCESS_TOKEN".equals(claims.get("type"));
        } catch (ExpiredJwtException e) {
            // Retorna si el token ha expirado
            System.out.println("El token ha expirado: " + e.getMessage());
            return false; // Maneja otra exepcion implicita en la expiración
        } catch (Exception e) {
            // Maneja otras excepciones (firma inválida, token malformado, etc.)
            System.out.println("Error al verificar el token: " + e.getMessage());
            return false;
        }
    }

    public boolean isRefreshTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    public String getUsernameFromRefreshToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.getSubject();
        } catch (Exception e) {
            return null; // O manejar la excepción de manera más específica si lo prefieres
        }
    }

    /***/
    private SecretKey getKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getEmailFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = getEmailFromToken(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token) && isAccessToken(token));
    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
}
