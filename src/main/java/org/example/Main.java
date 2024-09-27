package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        ConnectionPool connectionPool = ConnectionPool.getInstance(5); // Pool of size 5

        ExecutorService threadPool = Executors.newFixedThreadPool(7); // Thread pool with 7 threads

        for (int i = 0; i < 7; i++) {
            final int threadId = i + 1;
            threadPool.submit(() -> {
                try {
                    System.out.println("Thread " + threadId + " is waiting for a connection...");
                    Connection connection = connectionPool.getConnection();
                    System.out.println("Thread " + threadId + " acquired connection " + connection.getId());

                    // Simulate work
                    connection.use();

                    System.out.println("Thread " + threadId + " releasing connection " + connection.getId());
                    connectionPool.releaseConnection(connection);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.SECONDS); // Wait for all threads to finish
    }
}