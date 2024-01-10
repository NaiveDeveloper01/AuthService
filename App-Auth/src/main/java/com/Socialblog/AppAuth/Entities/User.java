package com.Socialblog.AppAuth.Entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(nullable = false,unique = true,length = 50)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false,unique = true,length = 50)
    private String email;

    @Column(nullable = false)
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "roleId")}
    )
    private Set<Role> roles;

    public User() {
    }

    public User(Long userId, String username, String password, String email, Set<Role> roles) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
