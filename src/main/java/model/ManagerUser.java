package model;
public class ManagerUser extends User {
    private String department;
    private int managedProjects;

    public ManagerUser(
            long id,
            String name,
            String email,
            String department,
            int managedProjects
    ) {
        super(id, name, email, UserRole.MANAGER);

        if (department == null || department.isBlank()) {
            throw new IllegalArgumentException(
                    "Отдел не может быть пустым"
            );
        }

        if (managedProjects < 0) {
            throw new IllegalArgumentException(
                    "Количество проектов не может быть отрицательным"
            );
        }

        this.department = department.trim();
        this.managedProjects = managedProjects;
    }

    public String getDepartment() {
        return department;
    }

    public int getManagedProjects() {
        return managedProjects;
    }

    public void addProject() {
        managedProjects++;
    }

    public void removeProject() {
        if (managedProjects > 0) {
            managedProjects--;
        }
    }

    public boolean canManageProjects() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Менеджер: " + getName()
                + ", email: " + getEmail()
                + ", отдел: " + department
                + ", проектов: " + managedProjects;
    }

    @Override
    public String toString() {
        return "ManagerUser{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", role=" + getRole() +
                ", department='" + department + '\'' +
                ", managedProjects=" + managedProjects +
                '}';
    }
}