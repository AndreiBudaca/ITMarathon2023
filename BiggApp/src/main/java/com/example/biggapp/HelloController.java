package com.example.biggapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Button LoginButton;
    @FXML
    private TextField LoginUsernameTextField;
    @FXML
    private TextField LoginPasswordTextField;
    @FXML
    private Label LoginErrorLabel;

    @FXML
    protected void onLoginClick() throws IOException {
        String username = LoginUsernameTextField.getText();
        String password = LoginPasswordTextField.getText();

        //TODO send request with user and pass

        //if successful, redirect to homepage:
        if(true) {
            Stage stage = (Stage) LoginButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("HomePage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
            stage.setScene(scene);
        }
        else {
            LoginErrorLabel.setVisible(true);
        }
    }
}