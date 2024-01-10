package com.Socialblog.AppAuth.Requests;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class RoleRequest {
    private Long roleId;
    private String role;

    public RoleRequest(Long roleId, String role) {
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
