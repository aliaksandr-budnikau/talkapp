package org.talkapp.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.talkapp.config.WebSecurityConfigurer;
import org.talkapp.model.LoginCredentials;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static org.talkapp.config.WebSecurityConfigurer.AUTHORIZATION_HEADER_PREFIX;

/**
 * @author Budnikau Aliaksandr
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;

    public JWTLoginFilter(String url, TokenProvider tokenProvider, ObjectMapper objectMapper) {
        super(new AntPathRequestMatcher(url));
        this.tokenProvider = tokenProvider;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {
        LoginCredentials creds = objectMapper.readValue(req.getInputStream(), LoginCredentials.class);
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        creds.getEmail(),
                        creds.getPassword(),
                        Collections.emptyList()
                )
        );
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {
        String token = tokenProvider.createToken(auth);
        res.addHeader(WebSecurityConfigurer.AUTHORIZATION_HEADER, AUTHORIZATION_HEADER_PREFIX + token);
        chain.doFilter(req, res);
    }
}