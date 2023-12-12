package org.example.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BeveragesDataSource {
    private Connection conn;

    public BeveragesDataSource(){
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:postgresql:test", "postgres","LabInstall2023");
        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void executeQuery(String query) {
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public Statement createStatement() {
        try {
            return conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
