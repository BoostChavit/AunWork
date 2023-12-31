package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

        public void start(Stage stage) throws Exception {
            Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        }


        public static void main(String[] args) {launch(args);}

}