package com.teamtreehouse.automation.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teamtreehouse.automation.core.BaseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;

@Entity
public class User extends BaseEntity {
    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    private String name;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String[] roles;

    protected User() {
        super();
    }

    public User(String name, String password, String[] roles) {
        this();
        this.name = name;
        setPassword(password);
        this.roles = roles;
    }

    public void setPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }
}
