module com.example.biggapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires httpcore;
    requires org.json;
    requires httpclient;
    requires com.google.gson;


    opens com.example.biggapp to javafx.fxml;
    opens com.example.biggapp.Model to com.google.gson;
    exports com.example.biggapp;
}