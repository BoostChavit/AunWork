package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.models.Beverages;
import org.example.services.BeveragesDataSource;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    @FXML
    private TextField tfId;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPrice;
    @FXML
    private TableView<Beverages> tvBeverages;
    @FXML
    private TableColumn<Beverages, Integer> colId;
    @FXML
    private TableColumn<Beverages, String> colName;
    @FXML
    private TableColumn<Beverages, Integer> colPrice;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;

    private ObservableList<Beverages> list;
    private BeveragesDataSource conn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        conn = new BeveragesDataSource();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {

        if(event.getSource() == btnInsert){
            insertRecord();
        }else if (event.getSource() == btnUpdate){
            updateRecord();
        }else if(event.getSource() == btnDelete){
            deleteButton();
        }

    }

    public ObservableList<Beverages> getBeveragesList(){
        ObservableList<Beverages> beverageList = FXCollections.observableArrayList();
        String query = "SELECT * FROM beverages";
        Statement st;
        ResultSet rs;

        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Beverages beverages;
            while(rs.next()){
                beverages = new Beverages(rs.getInt("id"), rs.getString("name"), rs.getInt("price"), rs.getInt("qty"));
                beverageList.add(beverages);
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return beverageList;
    }

    public void showBeverages(){
//        ObservableList<Beverages> list = getBeveragesList();

        colId.setCellValueFactory(new PropertyValueFactory<Beverages, Integer>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<Beverages, String>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<Beverages, Integer>("price"));

        tvBeverages.setItems(list);
    }
    private void insertRecord(){
        String query = "INSERT INTO beverages VALUES (" + tfId.getText() + ",'" + tfName.getText() + "','" + tfPrice.getText() + ")";
        conn.executeQuery(query);
        showBeverages();
    }
    private void updateRecord(){
        String query = "UPDATE  beverages SET name  = '" + tfName.getText() + "', price = '" + tfPrice.getText() + " WHERE id = " + tfId.getText() + "";
        conn.executeQuery(query);
        showBeverages();
    }
    private void deleteButton(){
        String query = "DELETE FROM books WHERE id =" + tfId.getText() + "";
        conn.executeQuery(query);
        showBeverages();
    }


}
