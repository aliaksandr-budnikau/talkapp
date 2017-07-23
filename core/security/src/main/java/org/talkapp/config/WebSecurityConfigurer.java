package org.talkapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.talkapp.filter.JWTFilter;
import org.talkapp.filter.JWTLoginFilter;
import org.talkapp.filter.TokenProvider;

/**
 * @author Budnikau Aliaksandr
 */
@EnableWebSecurity
@Configuration
@PropertySource(value = "classpath:security.properties")
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    public final static String AUTHORIZATION_HEADER = "Authorization";
    public final static String AUTHORIZATION_HEADER_PREFIX = "Bearer ";

    public static final String LOGIN = "/login";
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private ObjectMapper objectMapper;

    public JWTFilter jwtFilter() throws Exception {
        return new JWTFilter(tokenProvider);
    }

    public JWTLoginFilter jwtLoginFilter() throws Exception {
        JWTLoginFilter filter = new JWTLoginFilter(LOGIN, tokenProvider, objectMapper);
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtLoginFilter(), UsernamePasswordAuthenticationFilter.class);
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, LOGIN).permitAll()
                .anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("password")
                .roles("ADMIN");
    }
}