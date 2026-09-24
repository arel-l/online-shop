package com.example.kursovaya;
import model.AdminUser; import model.CustomerUser; import model.ManagerUser; import model.User; import model.UserRole;
import java.util.ArrayList; import java.util.Comparator; import java.util.List; import java.util.Map; import java.util.concurrent.ConcurrentHashMap; import java.util.concurrent.ExecutorService; import java.util.concurrent.Executors; import java.util.concurrent.TimeUnit;
public class KursovayaApplication {
    public static void main(String[] args) {

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

        printUsers(users);

        System.out.println("\n===== ПОИСК ПОЛЬЗОВАТЕЛЯ =====");

        User foundUser = findByEmail(
                users,
                "maria@shop.ru"
        );

        if (foundUser != null) {
            System.out.println("Найден:");
            System.out.println(foundUser);
            System.out.println(foundUser.getDescription());
        }

        System.out.println("\n===== ФИЛЬТРАЦИЯ ПО РОЛИ =====");

        List<User> managers = findByRole(
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

        printUsers(users);

        System.out.println("\n===== ДОБАВЛЕНИЕ =====");

        User newUser = new CustomerUser(
                6,
                "Олег Кузнецов",
                "oleg@client.ru",
                "ООО Альфа",
                45000
        );

        addUser(users, newUser);

        System.out.println(
                "Добавлен пользователь: " +
                        newUser.getName()
        );

        System.out.println("\n===== УДАЛЕНИЕ =====");

        boolean removed = removeById(users, 6);

        System.out.println(
                "Пользователь удалён: " + removed
        );

        System.out.println("\n===== СТАТИСТИКА =====");

        printStatistics(users);

        System.out.println("\n===== МНОГОПОТОЧНАЯ ОБРАБОТКА =====");

        processUsersInParallel(users);
    }

    public static void printUsers(List<User> users) {

        for (User user : users) {
            System.out.println(
                    "ID: " + user.getId() +
                            ", имя: " + user.getName() +
                            ", email: " + user.getEmail() +
                            ", роль: " + user.getRole()
            );
        }
    }

    public static User findByEmail(
            List<User> users,
            String email) {

        if (email == null || email.isBlank()) {
            return null;
        }

        return users.stream()
                .filter(user ->
                        user.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    public static List<User> findByRole(
            List<User> users,
            UserRole role) {

        return users.stream()
                .filter(user -> user.getRole() == role)
                .toList();
    }

    public static void addUser(
            List<User> users,
            User user) {

        if (user == null) {
            throw new IllegalArgumentException(
                    "Пользователь не может быть null");
        }

        boolean exists = users.stream()
                .anyMatch(existing ->
                        existing.getId() == user.getId());

        if (exists) {
            throw new IllegalArgumentException(
                    "Пользователь с таким ID уже существует");
        }

        users.add(user);
    }

    public static boolean removeById(
            List<User> users,
            long id) {

        return users.removeIf(
                user -> user.getId() == id
        );
    }

    public static void printStatistics(
            List<User> users) {

        Map<UserRole, Long> statistics =
                new java.util.EnumMap<>(UserRole.class);

        for (UserRole role : UserRole.values()) {
            statistics.put(
                    role,
                    users.stream()
                            .filter(user ->
                                    user.getRole() == role)
                            .count()
            );
        }

        for (Map.Entry<UserRole, Long> entry :
                statistics.entrySet()) {

            System.out.println(
                    entry.getKey() +
                            ": " +
                            entry.getValue()
            );
        }
    }

    public static void processUsersInParallel(
            List<User> users) {

        ExecutorService executor =
                Executors.newFixedThreadPool(3);

        Map<Long, String> results =
                new ConcurrentHashMap<>();

        for (User user : users) {

            executor.submit(() -> {

                String threadName =
                        Thread.currentThread().getName();

                String result =
                        threadName +
                                " обрабатывает пользователя " +
                                user.getName();

                results.put(
                        user.getId(),
                        result
                );

                System.out.println(result);
            });
        }

        executor.shutdown();

        try {
            if (!executor.awaitTermination(
                    10,
                    TimeUnit.SECONDS)) {

                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("\nРезультаты обработки:");

        results.values().forEach(
                System.out::println
        );
    }
}