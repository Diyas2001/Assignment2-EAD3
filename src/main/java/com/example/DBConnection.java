package com.example;
import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/ATMSystem";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1111";
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    String sql = "SELECT * FROM \"Customers\"";

    public void myInit() throws SQLException {
        System.out.println("Doing my initialization");
        System.out.println("Connecting to database");
        System.out.println("Creating statement");
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resultSet = statement.executeQuery(sql);
    }
    public void myDestroy() throws SQLException {
        System.out.println("Doing my destruction");
        System.out.println("Closing statement");
        System.out.println("Closing database");
        resultSet.close();
        statement.close();
        connection.close();
    }
    public void updateBalance(int balance, String name) throws SQLException {
        String sql = "UPDATE \"Customers\"" +
                "SET \"Balance\" = " + balance +
                "WHERE \"userName\" = '" + name + "';";
        int r = statement.executeUpdate(sql);
        if (r > 0) {
            System.out.println("Balance is updated");
        } else {
            System.out.println("Balance is not updated");
        }
    }
    public void beforeFirst() throws SQLException {
        resultSet.beforeFirst();
    }
    public ResultSet getResultSet() {
        return resultSet;
    }
}
