module com.example.biggapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires httpcore;
    requires org.json;
    requires httpclient;
    requires com.google.gson;


    opens com.example.biggapp to javafx.fxml;
    exports com.example.biggapp;
}