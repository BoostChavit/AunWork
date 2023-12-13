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
    private TextField tfQty;
    @FXML
    private TableView<Beverages> tvBeverages;
    @FXML
    private TableColumn<Beverages, Integer> colId;
    @FXML
    private TableColumn<Beverages, String> colName;
    @FXML
    private TableColumn<Beverages, Integer> colPrice;
    @FXML
    private TableColumn<Beverages, Integer> colQty;
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
        showBeverages();
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
        ResultSet rs;

        try{
            Statement st = conn.createStatement();
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
        list = getBeveragesList();

        TableColumn<Beverages, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(features -> features.getValue().nameProperty());
        nameCol.setMinWidth(100);

        TableColumn<Beverages, Integer> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(features -> features.getValue().priceProperty().asObject());
        priceCol.setMinWidth(100);

        TableColumn<Beverages, Integer> qtyCol = new TableColumn<>("Qty");
        qtyCol.setCellValueFactory(features -> features.getValue().qtyProperty().asObject());
        qtyCol.setMinWidth(100);


        tvBeverages.setItems(list);
        tvBeverages.getColumns().addAll(nameCol, priceCol, qtyCol);
    }

    public void updateBeverages() {
        list = getBeveragesList();
        tvBeverages.setItems(list);
    }

    private void insertRecord(){
        String  query = "INSERT INTO beverages (name, price, qty) VALUES (" + "'" + tfName.getText() + "'" + "," + tfPrice.getText() + "," + tfQty.getText() + ")";
        conn.executeQuery(query);
        updateBeverages();
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
