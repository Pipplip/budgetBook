package de.phbeinternational.budgetbook.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import de.phbeinternational.budgetbook.entity.User;
import de.phbeinternational.budgetbook.entity.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private final String secretKey = "B12FB0D940454D9B28F054C182095F233A4DB70773BE6D52B59F6F297616F19B";
//	private final long jwtExpiration = 1000 * 60 * 60 * 24 * 10; // 1000 * 60 * 60 * 24 = 1d
//	private final long jwtExpiration = 1000 * 60 * 60; // 1h
	private final long jwtExpiration = 1000 * 60 * 15; // 15m
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts
				.parser()
                .verifyWith(getSigningKey())//.setSigningKey(getSigningKey())
                .build()
                .parseSignedClaims(token)//.parseClaimsJws(token)
                .getPayload();//.getBody(); 
		
	}
	
	private SecretKey getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}
	
	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        // Extrahiere die Rolle aus den Authorities
        String role = userDetails.getAuthorities().stream()
                                .findFirst()
                                .map(grantedAuthority -> grantedAuthority.getAuthority())
                                .orElse(UserRole.USER.toString()); // Standardrolle, falls keine Rolle vorhanden ist
        
        extraClaims.put("role", role);  // Rolle als Claim hinzufügen
        
        User user = (User)userDetails;
        extraClaims.put("userId", user.getId());  // Adding the user ID as a claim
		
		return buildToken(extraClaims, userDetails, jwtExpiration);
	}
	
	private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
		return Jwts
				.builder()
				.claims(extraClaims)//.setClaims(extraClaims)
				.subject(userDetails.getUsername())//.setSubject(userDetails.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSigningKey(), Jwts.SIG.HS256)
				.compact();
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public Long extractUserId(String token) {
		return extractClaim(token, claims -> claims.get("userId", Long.class));  // Extrahiere den userId-Claim als String
	}
}
