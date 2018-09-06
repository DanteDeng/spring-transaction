package com.nature.springtransaction;

import java.sql.Connection;
import java.sql.DriverManager;

public class SpringTransactionApplication {

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nature-test?useSSL=false&serverTimezone=GMT", "root", "dy051.");
        //TransactionInterceptor
    }
}
