package org.talkapp.repository.impl;

import org.springframework.stereotype.Component;
import org.talkapp.mapping.AccountMapping;
import org.talkapp.repository.AccountRepository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Budnikau Aliaksandr
 */
@Component
public class AccountRepositoryImpl implements AccountRepository {
    public static final String QWE_0 = "qwe0";
    public static final String QWE_1 = "qwe1";
    private Map<String, AccountMapping> store = new HashMap<>();

    @PostConstruct
    public void init() {
        AccountMapping mapping = new AccountMapping();
        mapping.setId(QWE_0);
        mapping.setPassword("password0");
        mapping.setUsername("username0");
        store.put(mapping.getId(), mapping);

        mapping = new AccountMapping();
        mapping.setId(QWE_1);
        mapping.setPassword("password1");
        mapping.setUsername("username1");
        store.put(mapping.getId(), mapping);
    }

    @Override
    public AccountMapping findByUsername(String username) {
        for (Map.Entry<String, AccountMapping> entry : store.entrySet()) {
            if (entry.getValue().getUsername().equals(username)) {
                return entry.getValue();
            }
        }
        return null;
    }
}