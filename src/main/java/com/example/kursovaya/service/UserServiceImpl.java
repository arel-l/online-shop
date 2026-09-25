package com.example.kursovaya.service;
import com.example.kursovaya.model.User; import com.example.kursovaya.model.UserRole;
import java.util.EnumMap; import java.util.List; import java.util.Map;
public class UserServiceImpl implements UserService {
    @Override
    public void printUsers(List<User> users) {
        for (User user : users) {
            System.out.println(
                    "ID: " + user.getId() +
                            ", имя: " + user.getName() +
                            ", email: " + user.getEmail() +
                            ", роль: " + user.getRole()
            );
        }
    }

    @Override
    public User findByEmail(
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

    @Override
    public List<User> findByRole(
            List<User> users,
            UserRole role) {

        return users.stream()
                .filter(user -> user.getRole() == role)
                .toList();
    }

    @Override
    public void addUser(
            List<User> users,
            User user) {

        if (user == null) {
            throw new IllegalArgumentException(
                    "Пользователь не может быть null"
            );
        }

        boolean exists = users.stream()
                .anyMatch(existing ->
                        existing.getId() == user.getId()
                );

        if (exists) {
            throw new IllegalArgumentException(
                    "Пользователь с таким ID уже существует"
            );
        }

        users.add(user);
    }

    @Override
    public boolean removeById(
            List<User> users,
            long id) {

        return users.removeIf(
                user -> user.getId() == id
        );
    }

    @Override
    public void printStatistics(
            List<User> users) {

        Map<UserRole, Long> statistics =
                new EnumMap<>(UserRole.class);

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
}