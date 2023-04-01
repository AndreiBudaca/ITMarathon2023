package com.example.biggapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {

    @FXML
    private Label LoginErrorLabel;
    @FXML
    private TextField LoginUsernameTextField;
    @FXML
    private TextField LoginPasswordTextField;

    @FXML
    protected void onLoginClick() {
        String username = LoginUsernameTextField.getText();
        String password = LoginPasswordTextField.getText();

        //TODO: send request to API and:

        //TODO: Redirect if good
        //...

        //Display error if bad
        {
            LoginErrorLabel.setVisible(true);
        }
    }
}