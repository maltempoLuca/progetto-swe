package com.company.user;

public class User {
    public User(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    private final String name;
    private final String surname;
    private final String email;
}
