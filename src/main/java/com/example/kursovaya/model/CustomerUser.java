package com.example.kursovaya.model;
public class CustomerUser extends User {
    private String company;
    private double totalOrders;

    public CustomerUser(
            long id,
            String name,
            String email,
            String company,
            double totalOrders
    ) {
        super(id, name, email, UserRole.CUSTOMER);

        if (company == null || company.isBlank()) {
            throw new IllegalArgumentException(
                    "Название компании не может быть пустым"
            );
        }

        if (totalOrders < 0) {
            throw new IllegalArgumentException(
                    "Сумма заказов не может быть отрицательной"
            );
        }

        this.company = company.trim();
        this.totalOrders = totalOrders;
    }

    public String getCompany() {
        return company;
    }

    public double getTotalOrders() {
        return totalOrders;
    }

    public void addOrder(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Сумма заказа должна быть положительной"
            );
        }

        totalOrders += amount;
    }

    public void cancelOrder(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Сумма заказа должна быть положительной"
            );
        }

        if (amount > totalOrders) {
            throw new IllegalArgumentException(
                    "Нельзя отменить заказ на сумму больше общей суммы заказов"
            );
        }

        totalOrders -= amount;
    }

    @Override
    public String getDescription() {
        return "Клиент: " + getName()
                + ", email: " + getEmail()
                + ", компания: " + company
                + ", сумма заказов: " + totalOrders;
    }

    @Override
    public String toString() {
        return "CustomerUser{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", role=" + getRole() +
                ", company='" + company + '\'' +
                ", totalOrders=" + totalOrders +
                '}';
    }
}