package com.example.kursovaya.model;
public enum UserRole {
    ADMIN("Администратор", 3),
    MANAGER("Менеджер", 2),
    CUSTOMER("Клиент", 1),
    USER("Пользователь", 0);

    private final String description;
    private final int accessLevel;

    UserRole(String description, int accessLevel) {
        this.description = description;
        this.accessLevel = accessLevel;
    }

    public String getDescription() {
        return description;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public boolean hasHigherAccessThan(UserRole other) {
        return this.accessLevel > other.accessLevel;
    }

    @Override
    public String toString() {
        return description;
    }
}