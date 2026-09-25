package com.example.kursovaya.model;
import java.util.Objects;
public abstract class User {
    private long id;
    private String name;
    private String email;
    private UserRole role;

    public User(long id, String name, String email, UserRole role) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID должен быть положительным");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя пользователя не может быть пустым");
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email не может быть пустым");
        }

        if (role == null) {
            throw new IllegalArgumentException("Роль пользователя не может быть null");
        }

        this.id = id;
        this.name = name.trim();
        this.email = email.trim().toLowerCase();
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя пользователя не может быть пустым");
        }

        this.name = name.trim();
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email не может быть пустым");
        }

        this.email = email.trim().toLowerCase();
    }

    public void setRole(UserRole role) {
        if (role == null) {
            throw new IllegalArgumentException("Роль пользователя не может быть null");
        }

        this.role = role;
    }

    public abstract String getDescription();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof User user)) {
            return false;
        }

        return id == user.id &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
