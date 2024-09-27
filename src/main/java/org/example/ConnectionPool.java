package org.example;

import java.util.concurrent.*;

public class ConnectionPool {
    private final BlockingQueue<Connection> pool;

    // Lazy initialization
    private static ConnectionPool instance;

    private ConnectionPool(int size) {
        pool = new ArrayBlockingQueue<>(size);
        for (int i = 0; i < size; i++) {
            pool.add(new Connection(i + 1));
        }
    }

    public static synchronized ConnectionPool getInstance(int size) {
        if (instance == null) {
            instance = new ConnectionPool(size);
        }
        return instance;
    }

    public Connection getConnection() throws InterruptedException {
        return pool.take(); // Take a connection, waiting if necessary
    }

    public void releaseConnection(Connection connection) {
        pool.offer(connection); // Return the connection to the pool
    }
}
