package com.javaproject.server.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jws;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    /**
     * Security secret key.
     */
    private static final String SECRET_KEY = "your-very-strong-secret-key-to-sign-jwt";

    /**
     * Session Expiration time (1 hour).
     */
    private static final long EXPIRATION_TIME = 1000L * 60 * 60;

    /**
     * Generate JWT token.
     *
     * @param username value
     * @return generated token value
     */
    public String generateToken(final String username) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());  // Use a secure key

        return Jwts.builder()
                .setSubject(username)  // Set username as the subject
                .setIssuedAt(new Date())  // Set issue date
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))  // Set expiration date
                .signWith(key, SignatureAlgorithm.HS256)  // Sign with the secret key using HMAC-SHA256
                .compact();  // Generate and return the token
    }

    /**
     * Extract the username (subject) from the token.
     *
     * @param token value
     * @return username value
     */
    public String extractUsername(final String token) {
        return extractClaim(token, Claims::getSubject);  // Extract the subject (username)
    }

    /**
     * Extract Token expiration date.
     *
     * @param token value
     * @return expiration date
     */
    public Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Method to extract any claim from the token

    /**
     * Extract any claim from the token.
     *
     * @param token          value
     * @param claimsResolver value
     * @param <T>            Function that takes a Claims object and returns a value of type T
     * @return the function result
     */
    public <T> T extractClaim(final String token, final java.util.function.Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Get all claims from the token.
     *
     * @param token value
     * @return Claims value
     */
    private Claims extractAllClaims(final String token) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());  // Use the same key for parsing

        JwtParser jwtParser = Jwts.parserBuilder()  // Use the new parserBuilder API
                .setSigningKey(key)  // Set the signing key
                .build();  // Build the parser

        Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);  // Parse the token
        return claimsJws.getBody();  // Return the claims
    }

    /**
     * Check if the token is expired.
     *
     * @param token value
     * @return if token is expired
     */
    private Boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());  // Compare expiration with current date
    }

    /**
     * Validate the token (check if it is expired and if the username matches).
     *
     * @param token    value
     * @param username value
     * @return validation result
     */
    public Boolean validateToken(final String token, final String username) {
        String extractedUsername = extractUsername(token);  // Extract the username from the token
        return (extractedUsername.equals(username) && !isTokenExpired(token));  // Ensure username matches and token is not expired
    }

}
