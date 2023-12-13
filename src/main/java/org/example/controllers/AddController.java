package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.services.BeveragesDataSource;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddController implements Initializable {

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPrice;
    @FXML
    private TextField tfQty;
    @FXML
    private Button btnInsert;

    private BeveragesDataSource conn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        conn = new BeveragesDataSource();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException {
        String name = tfName.getText();
        String price = tfPrice.getText();
        String qty = tfQty.getText();

        if(!name.isEmpty() && !price.isEmpty() && !qty.isEmpty()) {
            String  query = "INSERT INTO beverages (name, price, qty) VALUES (" + "'" + name + "'" + "," + price + "," + qty + ")";
            conn.executeQuery(query);

            Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  // Get the current stage
            stage.setScene(scene);
            stage.show();

        }
    }

    @FXML
    public void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  // Get the current stage
        stage.setScene(scene);
        stage.show();
    }
}
