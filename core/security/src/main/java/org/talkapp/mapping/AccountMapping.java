package org.talkapp.mapping;

/**
 * @author Budnikau Aliaksandr
 */
public class AccountMapping {

    private String id;
    private String username;
    private String password;

    public AccountMapping() {
    }

    public AccountMapping(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}