package org.talkapp.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static java.util.Collections.emptyList;

/**
 * @author Budnikau Aliaksandr
 */
@Component
public class TokenProvider {

    @Value("${spring.security.authentication.jwt.validity}")
    private long tokenValidityInMilliSeconds;

    @Value("${spring.security.authentication.jwt.secret}")
    private String secretKey;

    public String createToken(Authentication authentication) {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime expirationDateTime = now.plus(this.tokenValidityInMilliSeconds, ChronoUnit.MILLIS);

        Date issueDate = Date.from(now.toInstant());
        Date expirationDate = Date.from(expirationDateTime.toInstant());

        return Jwts.builder().setSubject(authentication.getName())
                .signWith(SignatureAlgorithm.HS512, secretKey).setIssuedAt(issueDate).setExpiration(expirationDate).compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, emptyList());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            return false;
        }
    }
}