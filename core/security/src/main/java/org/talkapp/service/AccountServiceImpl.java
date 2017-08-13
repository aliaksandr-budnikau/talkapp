package org.talkapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.talkapp.mapping.AccountMapping;
import org.talkapp.model.Account;
import org.talkapp.repository.AccountRepository;

/**
 * @author Budnikau Aliaksandr
 */
@Component
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository repository;

    @Override
    public Account getCurrent() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AccountMapping mapping = repository.findByEmail((String) auth.getPrincipal());
        return toDto(mapping);
    }

    private Account toDto(AccountMapping mapping) {
        Account account = new Account();
        account.setId(mapping.getId());
        account.setPassword(mapping.getPassword());
        account.setEmail(mapping.getEmail());
        return account;
    }
}