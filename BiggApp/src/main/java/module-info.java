module com.example.biggapp {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.biggapp to javafx.fxml;
    exports com.example.biggapp;
}