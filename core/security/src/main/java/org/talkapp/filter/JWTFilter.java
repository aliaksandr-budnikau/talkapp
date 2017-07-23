package org.talkapp.filter;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.talkapp.config.WebSecurityConfigurer.AUTHORIZATION_HEADER;
import static org.talkapp.config.WebSecurityConfigurer.AUTHORIZATION_HEADER_PREFIX;

/**
 * @author Budnikau Aliaksandr
 */
public class JWTFilter extends GenericFilterBean {

    private final TokenProvider tokenProvider;

    public JWTFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException,
            ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String jwt = resolveToken(httpServletRequest);
            if (StringUtils.hasText(jwt)) {
                if (tokenProvider.validateToken(jwt)) {
                    Authentication authentication = tokenProvider.getAuthentication(jwt);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            chain.doFilter(servletRequest, servletResponse);

            this.resetAuthenticationAfterRequest();
        } catch (ExpiredJwtException eje) {
            ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private void resetAuthenticationAfterRequest() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(AUTHORIZATION_HEADER_PREFIX)) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}