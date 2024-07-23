package com.blog.tech.domain.common;

import java.sql.Connection;

public class ConnectionManager {

    private static ThreadLocal<Connection> threadLocalConnection = new ThreadLocal<>();

    public static void setConnection(final Connection connection) {
        threadLocalConnection.set(connection);
    }

    public static Connection getConnection() {
        return threadLocalConnection.get();
    }

    public static void removeConnection() {
        threadLocalConnection.remove();
    }

}
