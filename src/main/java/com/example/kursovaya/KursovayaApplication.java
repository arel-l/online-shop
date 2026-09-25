package com.example.kursovaya;
import com.example.kursovaya.model.AdminUser; import com.example.kursovaya.model.CustomerUser; import com.example.kursovaya.model.ManagerUser; import com.example.kursovaya.model.User; import com.example.kursovaya.model.UserRole; import com.example.kursovaya.service.UserProcessingService; import com.example.kursovaya.service.UserProcessingServiceImpl; import com.example.kursovaya.service.UserService; import com.example.kursovaya.service.UserServiceImpl; import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.ArrayList; import java.util.Comparator; import java.util.List;
@SpringBootApplication public class KursovayaApplication {
    public static void main(String[] args) {

        UserService userService =
                new UserServiceImpl();

        UserProcessingService processingService =
                new UserProcessingServiceImpl();

        List<User> users = new ArrayList<>();

        users.add(new AdminUser(
                1,
                "Анна Петрова",
                "anna@shop.ru",
                15
        ));

        users.add(new ManagerUser(
                2,
                "Иван Смирнов",
                "ivan@shop.ru",
                "Отдел продаж",
                4
        ));

        users.add(new ManagerUser(
                3,
                "Мария Орлова",
                "maria@shop.ru",
                "Отдел заказов",
                7
        ));

        users.add(new CustomerUser(
                4,
                "Алексей Волков",
                "alex@client.ru",
                "ООО Ромашка",
                125000
        ));

        users.add(new CustomerUser(
                5,
                "Елена Соколова",
                "elena@client.ru",
                "ООО Вектор",
                85000
        ));

        System.out.println("===== СПИСОК ПОЛЬЗОВАТЕЛЕЙ =====");

        userService.printUsers(users);

        System.out.println("\n===== ПОИСК ПОЛЬЗОВАТЕЛЯ =====");

        User foundUser = userService.findByEmail(
                users,
                "maria@shop.ru"
        );

        if (foundUser != null) {
            System.out.println("Найден:");
            System.out.println(foundUser);
            System.out.println(foundUser.getDescription());
        }

        System.out.println("\n===== ФИЛЬТРАЦИЯ ПО РОЛИ =====");

        List<User> managers = userService.findByRole(
                users,
                UserRole.MANAGER
        );

        managers.forEach(user ->
                System.out.println(user.getDescription())
        );

        System.out.println("\n===== СОРТИРОВКА =====");

        users.sort(
                Comparator.comparing(User::getName)
        );

        userService.printUsers(users);

        System.out.println("\n===== ДОБАВЛЕНИЕ =====");

        User newUser = new CustomerUser(
                6,
                "Олег Кузнецов",
                "oleg@client.ru",
                "ООО Альфа",
                45000
        );

        userService.addUser(users, newUser);

        System.out.println(
                "Добавлен пользователь: " +
                        newUser.getName()
        );

        System.out.println("\n===== УДАЛЕНИЕ =====");

        boolean removed = userService.removeById(
                users,
                6
        );

        System.out.println(
                "Пользователь удалён: " + removed
        );

        System.out.println("\n===== СТАТИСТИКА =====");

        userService.printStatistics(users);

        System.out.println("\n===== МНОГОПОТОЧНАЯ ОБРАБОТКА =====");

        processingService.processUsersInParallel(users);
    }
}