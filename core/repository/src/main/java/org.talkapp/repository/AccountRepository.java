package org.talkapp.repository;

import org.talkapp.mapping.AccountMapping;

/**
 * @author Budnikau Aliaksandr
 */
public interface AccountRepository {

    AccountMapping findByEmail(String email);
}