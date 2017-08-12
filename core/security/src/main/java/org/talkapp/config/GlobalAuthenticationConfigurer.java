package org.talkapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.talkapp.mapping.AccountMapping;
import org.talkapp.repository.AccountRepository;

/**
 * @author Budnikau Aliaksandr
 */
@Configuration
public class GlobalAuthenticationConfigurer extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(email -> {
            AccountMapping account = accountRepository.findByEmail(email);
            if (account == null) {
                throw new UsernameNotFoundException("could not find the user '"
                        + email + "'");
            } else {
                return new User(account.getEmail(), account.getPassword(), true, true, true, true,
                        AuthorityUtils.createAuthorityList("USER"));
            }
        });
    }
}
