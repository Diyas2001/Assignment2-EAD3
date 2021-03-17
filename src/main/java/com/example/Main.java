package com.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) throws SQLException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "bean.xml");
        DBConnection connection = context.getBean("dbConnection", DBConnection.class);
        Scanner s = new Scanner(System.in);

        loop: while (true) {
            System.out.println("Enter the name");
            String name = s.next();
            ResultSet resultSet = connection.getResultSet();
            String UserName = null;
            int CardNumber = 0;
            int PinCode = 0;
            int balance = 0;
            while (resultSet.next()) {
                if (resultSet.getString("userName").equalsIgnoreCase(name)) {
                    UserName = resultSet.getString("userName");
                    CardNumber = resultSet.getInt("cardNumber");
                    PinCode = resultSet.getInt("pinCode");
                    balance = resultSet.getInt("Balance");
                    break;
                }
            }
            if (UserName != null) {
                System.out.println("Enter the card number");
                int cardNumber = s.nextInt();
                System.out.println("Enter the pin code");
                int pinCode = s.nextInt();
                if (CardNumber == cardNumber && PinCode == pinCode) {
                    System.out.println("Choose operation");
                    System.out.println("1. Check balance" +
                            "\n2. Withdraw" +
                            "\n3. Top up" +
                            "\n4. Exit");
                    int ch = s.nextInt();
                    switch (ch) {
                        case 1:
                            System.out.println("Your balance is: " + balance);
                            break;
                        case 2:
                            System.out.println("Enter withdraw amount");
                            int withdraw = s.nextInt();
                            if (withdraw <= balance) {
                                connection.updateBalance(balance - withdraw, UserName);
                            } else {
                                System.out.println("Your balance is less than " + withdraw);
                            }
                            System.out.println("Now your balance is: " + balance);
                            break;
                        case 3:
                            System.out.println("Enter top up amount");
                            int topUp = s.nextInt();
                            connection.updateBalance(balance + topUp, UserName);
                            System.out.println("Now your balance is: " + balance);
                            break;
                        case 4:
                            break loop;
                    }
                } else {
                    System.out.println("Pin code or password is incorrect");
                }
            } else {
                System.out.println("Account not found");
            }
            connection.beforeFirst();
        }
        context.close();
    }
}
