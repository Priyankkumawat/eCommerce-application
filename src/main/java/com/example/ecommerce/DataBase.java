package com.example.ecommerce;

import java.sql.*;

public class DataBase {
    static String URL="jdbc:mysql://localhost:3306/ecommerce";
    static String user="root";
    static String pass="";

    private static Statement getStatement(){
        try{
            Connection conn = DriverManager.getConnection(URL,user,pass);
            return conn.createStatement();
        }
        catch ( Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet getQuery(String query){
        Statement statement = getStatement();
        try{
            return statement.executeQuery(query);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static int setQuery(String query){
        Statement statement = getStatement();
        try{
            return statement.executeUpdate(query);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        String q="select * from products";
        ResultSet rc= getQuery(q);
        if(rc != null){
            System.out.println("Connected");
        }
    }
}
