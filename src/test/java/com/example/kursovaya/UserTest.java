package com.example.kursovaya;
import com.example.kursovaya.model.AdminUser; import com.example.kursovaya.model.CustomerUser; import com.example.kursovaya.model.ManagerUser; import com.example.kursovaya.model.User; import com.example.kursovaya.model.UserRole; import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UserTest {
// =========================
// Тест AdminUser
// =========================

    @Test
    void testAdminUserCreation() {
        AdminUser admin = new AdminUser(
                1L,
                "Анна Петрова",
                "anna@mail.ru",
                5
        );

        assertEquals(1L, admin.getId());
        assertEquals("Анна Петрова", admin.getName());
        assertEquals("anna@mail.ru", admin.getEmail());
        assertEquals(UserRole.ADMIN, admin.getRole());
        assertEquals(5, admin.getManagedUsersCount());
    }

    @Test
    void testAdminUserAddManagedUser() {
        AdminUser admin = new AdminUser(
                1L,
                "Анна Петрова",
                "anna@mail.ru",
                5
        );

        admin.addManagedUser();

        assertEquals(6, admin.getManagedUsersCount());
    }

    @Test
    void testAdminUserRemoveManagedUser() {
        AdminUser admin = new AdminUser(
                1L,
                "Анна Петрова",
                "anna@mail.ru",
                5
        );

        admin.removeManagedUser();

        assertEquals(4, admin.getManagedUsersCount());
    }

    @Test
    void testAdminUserNegativeCount() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AdminUser(
                        1L,
                        "Анна Петрова",
                        "anna@mail.ru",
                        -1
                )
        );
    }

// =========================
// Тест ManagerUser
// =========================

    @Test
    void testManagerUserCreation() {
        ManagerUser manager = new ManagerUser(
                2L,
                "Иван Смирнов",
                "ivan@mail.ru",
                "Отдел продаж",
                3
        );

        assertEquals(2L, manager.getId());
        assertEquals("Иван Смирнов", manager.getName());
        assertEquals("ivan@mail.ru", manager.getEmail());
        assertEquals(UserRole.MANAGER, manager.getRole());
        assertEquals("Отдел продаж", manager.getDepartment());
        assertEquals(3, manager.getManagedOrders());
    }

    @Test
    void testManagerUserAddOrder() {
        ManagerUser manager = new ManagerUser(
                2L,
                "Иван Смирнов",
                "ivan@mail.ru",
                "Отдел продаж",
                3
        );

        manager.addOrder();

        assertEquals(4, manager.getManagedOrders());
    }

    @Test
    void testManagerUserRemoveOrder() {
        ManagerUser manager = new ManagerUser(
                2L,
                "Иван Смирнов",
                "ivan@mail.ru",
                "Отдел продаж",
                3
        );

        manager.removeOrder();

        assertEquals(2, manager.getManagedOrders());
    }

    @Test
    void testManagerUserEmptyDepartment() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ManagerUser(
                        2L,
                        "Иван Смирнов",
                        "ivan@mail.ru",
                        "",
                        3
                )
        );
    }

// =========================
// Тест CustomerUser
// =========================

    @Test
    void testCustomerUserCreation() {
        CustomerUser customer = new CustomerUser(
                3L,
                "Петр Петров",
                "petr@mail.ru",
                "ООО Ромашка",
                10000.0
        );

        assertEquals(3L, customer.getId());
        assertEquals("Петр Петров", customer.getName());
        assertEquals("petr@mail.ru", customer.getEmail());
        assertEquals(UserRole.CUSTOMER, customer.getRole());
        assertEquals("ООО Ромашка", customer.getCompany());
        assertEquals(10000.0, customer.getTotalOrders());
    }

    @Test
    void testCustomerAddOrder() {
        CustomerUser customer = new CustomerUser(
                3L,
                "Петр Петров",
                "petr@mail.ru",
                "ООО Ромашка",
                10000.0
        );

        customer.addOrder(5000.0);

        assertEquals(15000.0, customer.getTotalOrders());
    }

    @Test
    void testCustomerNegativeOrder() {
        CustomerUser customer = new CustomerUser(
                3L,
                "Петр Петров",
                "petr@mail.ru",
                "ООО Ромашка",
                10000.0
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> customer.addOrder(-500.0)
        );
    }

    @Test
    void testCustomerEmptyCompany() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new CustomerUser(
                        3L,
                        "Петр Петров",
                        "petr@mail.ru",
                        "",
                        10000.0
                )
        );
    }

// =========================
// Проверка полиморфизма
// =========================

    @Test
    void testPolymorphism() {
        User admin = new AdminUser(
                1L,
                "Анна Петрова",
                "anna@mail.ru",
                5
        );

        User manager = new ManagerUser(
                2L,
                "Иван Смирнов",
                "ivan@mail.ru",
                "Отдел продаж",
                3
        );

        User customer = new CustomerUser(
                3L,
                "Петр Петров",
                "petr@mail.ru",
                "ООО Ромашка",
                10000.0
        );

        assertEquals(UserRole.ADMIN, admin.getRole());
        assertEquals(UserRole.MANAGER, manager.getRole());
        assertEquals(UserRole.CUSTOMER, customer.getRole());
    }

// =========================
// Проверка toString()
// =========================

    @Test
    void testToString() {
        CustomerUser customer = new CustomerUser(
                3L,
                "Петр Петров",
                "petr@mail.ru",
                "ООО Ромашка",
                10000.0
        );

        String result = customer.toString();

        assertNotNull(result);
        assertTrue(result.contains("Петр Петров"));
        assertTrue(result.contains("ООО Ромашка"));
    }
}