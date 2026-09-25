package com.example.kursovaya.service;
import com.example.kursovaya.model.User; import com.example.kursovaya.model.UserRole;
import java.util.List;
public interface UserService {
    void printUsers(List<User> users);

    User findByEmail(List<User> users, String email);

    List<User> findByRole(List<User> users, UserRole role);

    void addUser(List<User> users, User user);

    boolean removeById(List<User> users, long id);

    void printStatistics(List<User> users);
}
