package com.example.kursovaya.model;
public class AdminUser extends User {
    private int managedUsersCount;

    public AdminUser(
            long id,
            String name,
            String email,
            int managedUsersCount
    ) {
        super(id, name, email, UserRole.ADMIN);

        if (managedUsersCount < 0) {
            throw new IllegalArgumentException(
                    "Количество пользователей не может быть отрицательным"
            );
        }

        this.managedUsersCount = managedUsersCount;
    }

    public int getManagedUsersCount() {
        return managedUsersCount;
    }

    public void addManagedUser() {
        managedUsersCount++;
    }

    public void removeManagedUser() {
        if (managedUsersCount > 0) {
            managedUsersCount--;
        }
    }

    public boolean canManageUsers() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Администратор: " + getName()
                + ", email: " + getEmail()
                + ", управляемых пользователей: " + managedUsersCount;
    }

    @Override
    public String toString() {
        return "AdminUser{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", role=" + getRole() +
                ", managedUsersCount=" + managedUsersCount +
                '}';
    }
}