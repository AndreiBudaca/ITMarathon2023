package com.example.biggapp;

import com.example.biggapp.Request.APICaller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppController {
    //login page
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
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("HomePage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
            stage.setScene(scene);
        }
        else {
            LoginErrorLabel.setVisible(true);
        }
    }

    //home page
    @FXML
    private TextField HomeSearchBarTextField;
    @FXML
    private VBox SearchResultsVBox;
    @FXML
    private Button MyRequestsButton;
    @FXML
    private Button SendRequestsButton;
    @FXML
    private Button LogoutButton;
    @FXML
    private Button MyProfileButton;
    @FXML
    protected void onHomeSearchBarUpdated(){
        //iterate user list at database level and return users that have
        List<String> users = List.of("Mircea Ion", "Costel Mihaita", "Andrei Budaca");

        //get input
        String inputText = HomeSearchBarTextField.getText();

        //clear existing results
        SearchResultsVBox.getChildren().clear();

        //get valid results
        List<String> validUsers = new ArrayList<>();
        if(!inputText.isEmpty()) {
            for (String user : users) {
                if (user.contains(inputText)) {
                    validUsers.add(user);
                }
            }
        }

        //create and display valid results
        for(String result: validUsers){
            Button resultLabel = new Button();
            resultLabel.setText(result);
            SearchResultsVBox.getChildren().add(resultLabel);
        }

    }
    @FXML
    protected void onMyRequestsClick(){

    }
    @FXML
    protected void onSendRequestClick() throws IOException {
        Stage stage = (Stage) LogoutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SendRequestPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setScene(scene);
    }
    @FXML
    protected void onLogoutClick() throws IOException {
        //TODO send logout request

        //if successful, redirect to homepage:
        if(true) {
            Stage stage = (Stage) LogoutButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LoginPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
            stage.setScene(scene);
        }

    }
    @FXML
    protected void onMyProfileClick(){

    }

    //send request page

}