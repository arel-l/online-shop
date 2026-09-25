package com.example.kursovaya.service;
import com.example.kursovaya.model.User;
import java.util.List; import java.util.Map; import java.util.concurrent.ConcurrentHashMap; import java.util.concurrent.ExecutorService; import java.util.concurrent.Executors; import java.util.concurrent.TimeUnit;
public class UserProcessingServiceImpl implements UserProcessingService {
    @Override
    public void processUsersInParallel(List<User> users) {

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