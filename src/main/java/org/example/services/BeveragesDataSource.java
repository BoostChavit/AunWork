package org.example.services;

import java.sql.*;

public class BeveragesDataSource {
    private Connection conn;

    public BeveragesDataSource(){
        try{
            conn = DriverManager.getConnection("jdbc:postgresql:test", "postgres","LabInstall2023");
            System.out.println("Connect to database successfully.");

            boolean tableExists = false;

            DatabaseMetaData metaData = conn.getMetaData();
            try (ResultSet tables = metaData.getTables(null, null, "beverages", null)) {
                tableExists = tables.next();
            }

            if (tableExists) {
                System.out.println("Table 'beverages' exists.");
            } else {
                String createTableSQL = "CREATE TABLE beverages (" +
                        "id SERIAL PRIMARY KEY," +
                        "name VARCHAR(255)," +
                        "price INTEGER," +
                        "qty INTEGER" +
                        ")";

                Statement statement = conn.createStatement();
                statement.executeUpdate(createTableSQL);
                System.out.println("Table 'beverages' created successfully.");

            }

        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void executeQuery(String query) {
        try{
            Statement st = conn.createStatement();
            int numAffect = st.executeUpdate(query);
            System.out.println(numAffect);
            st.close();
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
