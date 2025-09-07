package co.com.cristiancabarcas.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.Tuple;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${security.jwt.secret}")
    private String secretKey;

    @Value("${security.jwt.expiration}")
    private long expiration;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public Tuple<String, Date> generateToken(String username, String role) {
        Date expirationDate = new Date(System.currentTimeMillis() + expiration * 1000);

        String jwt = Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setExpiration(expirationDate)
                .signWith(getKey())
                .compact();

        return new Tuple<>("Bearer " + jwt, expirationDate);
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }


    public String extractRoles(String token) {
        return getClaims(token).get("role", String.class);
    }

    public boolean isValidToken(String token) {
        try {
            return getClaims(token).getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}