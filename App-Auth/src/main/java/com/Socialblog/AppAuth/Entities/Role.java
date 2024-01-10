package com.Socialblog.AppAuth.Entities;


import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    private Long roleId;

    @Column(nullable = false)
    private String role;

    public Role() {
    }

    public Role(Long roleId, String role) {
        this.roleId = roleId;
        this.role = role;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
