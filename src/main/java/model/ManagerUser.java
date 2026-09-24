package model;
public class ManagerUser extends User {
    private String department;
    private int managedOrders;

    public ManagerUser(
            long id,
            String name,
            String email,
            String department,
            int managedOrders
    ) {
        super(id, name, email, UserRole.MANAGER);

        if (department == null || department.isBlank()) {
            throw new IllegalArgumentException(
                    "Отдел не может быть пустым"
            );
        }

        if (managedOrders < 0) {
            throw new IllegalArgumentException(
                    "Количество заказов не может быть отрицательным"
            );
        }

        this.department = department.trim();
        this.managedOrders = managedOrders;
    }

    public String getDepartment() {
        return department;
    }

    public int getManagedOrders() {
        return managedOrders;
    }

    public void addOrder() {
        managedOrders++;
    }

    public void removeOrder() {
        if (managedOrders > 0) {
            managedOrders--;
        }
    }

    public boolean canManageOrders() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Менеджер: " + getName()
                + ", email: " + getEmail()
                + ", отдел: " + department
                + ", обрабатываемых заказов: " + managedOrders;
    }

    @Override
    public String toString() {
        return "ManagerUser{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", role=" + getRole() +
                ", department='" + department + '\'' +
                ", managedOrders=" + managedOrders +
                '}';
    }
}