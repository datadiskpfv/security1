package uk.co.datadisk.security1.entities;

import java.util.ArrayList;
import java.util.List;

public class User extends UserDetail {

    private Long id;

    private String username;
    private String password;

    private List<Role> roles;

    public User(Long id, boolean locked, boolean expired, boolean enabled, String username) {
        super(locked, expired, enabled);
        this.username = username;
        this.id = id;

        roles = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}
