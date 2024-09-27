package org.example;

public class Connection {
    private final int id;

    public Connection(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    // Simulate the usage of a connection
    public void use() throws InterruptedException {
        Thread.sleep(1000); // Simulates connection work
    }
}
