package org.example.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.example.models.Beverages;
import org.example.services.BeveragesDataSource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPrice;
    @FXML
    private TextField tfQty;
    @FXML
    private TableView<Beverages> tvBeverages;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;

    private ObservableList<Beverages> list;
    private BeveragesDataSource conn;
    private Beverages selected;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        conn = new BeveragesDataSource();
        selected = null;
        showBeverages();
        isSelected();
        handleSelectedTableView();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {

        if(event.getSource() == btnInsert){
            try {
                insertRecord(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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

    private void handleSelectedTableView() {
        tvBeverages.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Beverages>() {
                    @Override
                    public void changed(ObservableValue<? extends Beverages> observable, Beverages oldB, Beverages newB) {
                        selected = newB;
                        tfName.setText(newB.getName());
                        tfPrice.setText(String.valueOf(newB.getPrice()));
                        tfQty.setText(String.valueOf(newB.getQty()));
                        isSelected();
                    }
                });
    }

    public void updateBeverages() {
        list = getBeveragesList();
        tvBeverages.setItems(list);
    }

    public void isSelected() {
        if(selected == null) {
            tfName.setDisable(true);
            tfQty.setDisable(true);
            tfPrice.setDisable(true);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
        } else {
            tfName.setDisable(false);
            tfQty.setDisable(false);
            tfPrice.setDisable(false);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    @FXML
    public void insertRecord(ActionEvent event) throws IOException{
//        String  query = "INSERT INTO beverages (name, price, qty) VALUES (" + "'" + tfName.getText() + "'" + "," + tfPrice.getText() + "," + tfQty.getText() + ")";
//        conn.executeQuery(query);
//        updateBeverages();

        Parent root = FXMLLoader.load(getClass().getResource("/Add.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  // Get the current stage
        stage.setScene(scene);
        stage.show();
    }
    private void updateRecord(){
        String query = "UPDATE  beverages SET name  = '" + tfName.getText() + "', price = " + tfPrice.getText() + ", qty = " + tfQty.getText() + " WHERE id = " + selected.getId() + "";
        conn.executeQuery(query);
        updateBeverages();
        clear();
        isSelected();
    }
    private void deleteButton(){
        String query = "DELETE FROM beverages WHERE id =" + selected.getId() + "";
        conn.executeQuery(query);
        updateBeverages();
        clear();
        isSelected();
    }

    private void clear() {
        tfName.clear();
        tfQty.clear();
        tfPrice.clear();
        selected = null;
    }

    @FXML
    public void handleBackUpButton(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory to Save CSV");

        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory != null) {
            String csvFilePath = selectedDirectory.getAbsolutePath() + "/output.csv";

            try {
                Statement statement = conn.createStatement();
                ResultSet result = statement.executeQuery("SELECT * FROM beverages");
                ResultSetMetaData rsmd = result.getMetaData();

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {

                    for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
                        writer.write(rsmd.getColumnName(i) + ", ");
                    }

                    writer.newLine();

                    while (result.next()) {
                        writer.write(result.getInt("id") + ", " + result.getString("name") + ", " + result.getInt("price") + ", " + result.getInt("qty"));
                        writer.newLine();
                    }

                    System.out.println("Backup Database Successfully");

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Backup successfully at " + selectedDirectory);

                    alert.showAndWait();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
