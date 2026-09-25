package com.example.kursovaya.service;
import com.example.kursovaya.model.User;
import java.util.List;
public interface UserProcessingService {
    void processUsersInParallel(List<User> users);
}
