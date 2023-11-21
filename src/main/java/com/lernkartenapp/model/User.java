package com.lernkartenapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
    @Id
    private String id;
    private String email;
    private String password;

    // Getter für id
    public String getId() {
        return id;
    }

    // Setter für id
    public void setId(String id) {
        this.id = id;
    }

    // Getter für email
    public String getEmail() {
        return email;
    }

    // Setter für email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter für password
    public String getPassword() {
        return password;
    }

    // Setter für password
    public void setPassword(String password) {
        this.password = password;
    }
}
