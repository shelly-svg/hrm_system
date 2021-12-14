package org.example.api.entity;

public enum Permission {

    AUTH_READ("auth:read"), USERS_READ("users:read"), USERS_WRITE("users:write");

    private final String permissionName;

    Permission(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermission() {
        return permissionName;
    }

}
