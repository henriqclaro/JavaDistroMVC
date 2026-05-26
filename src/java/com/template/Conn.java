package com.template;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn {

    private static final String CONNECTION = "jdbc:postgresql://localhost:5432/Distros";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public Connection connectDB() {
        try {
            return DriverManager.getConnection(CONNECTION, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

