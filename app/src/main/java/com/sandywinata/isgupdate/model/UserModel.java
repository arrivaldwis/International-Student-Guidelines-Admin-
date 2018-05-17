package com.sandywinata.isgupdate.model;

public class UserModel {
    public String name;
    public String email;
    public String role;

    public UserModel(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public UserModel() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
