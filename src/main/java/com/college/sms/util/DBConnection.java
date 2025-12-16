package com.college.sms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Connection Utility Class
 * Manages database connections for the Student Management System
 */
public class DBConnection {
    
    private static final String URL = "jdbc:mysql://localhost:3306/student_ms";
    private static final String USER = "root";
    private static final String PASSWORD = "your_mysql_password"; // ⚠️ CHANGE THIS
    
    /**
     * Get a connection to the database
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    /**
     * Test database connection
     */
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("✅ Database Connected Successfully!");
                System.out.println("Database: " + conn.getCatalog());
                System.out.println("Driver: " + conn.getMetaData().getDriverName());
            }
        } catch (SQLException e) {
            System.err.println("❌ Connection Failed: " + e.getMessage());
            System.err.println("\n⚠️ Make sure to:");
            System.err.println("1. Update password in DBConnection.java");
            System.err.println("2. Create database using schema.sql");
            System.err.println("3. Ensure MySQL server is running");
        }
    }
}
