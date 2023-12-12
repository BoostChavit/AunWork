module example.org {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example to javafx.fxml;
    exports org.example;

    opens org.example.controllers to javafx.fxml;
    exports org.example.controllers;
}