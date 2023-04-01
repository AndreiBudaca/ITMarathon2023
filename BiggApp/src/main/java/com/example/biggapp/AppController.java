package com.example.biggapp;

import com.example.biggapp.Request.APICaller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AppController{
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
        Main.changeScene("SendRequestPage.fxml");
    }
    @FXML
    protected void onLogoutClick() throws IOException {
        //TODO send logout request

        //if successful, redirect to homepage:
        if(true) {
            Main.changeScene("LoginPage.fxml");
        }

    }
    @FXML
    protected void onMyProfileClick(){

    }

    //send request page
    @FXML
    private Button SendRequestBackButton;
    @FXML
    private Button SendRequestSendButton;
    @FXML
    private TextArea InvitedPersonsTextArea;
    @FXML
    private Button AddPersonButton;
    @FXML
    private TextField LocationTextField;
    @FXML
    private TextArea CommentTextArea;

    @FXML
    protected void onAddPersonClick(){

    }

    @FXML
    protected void onSendRequestSendClick(){

    }

    @FXML
    protected void onSendRequestBackClick() throws IOException {
        //Create pop-up
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner((Stage) SendRequestBackButton.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SendRequestBackPopUp.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 450, 150);
        dialog.setScene(scene);
        dialog.show();
    }

    //send request back pop-up
    @FXML
    private Button SendRequestBackCancelButton;
    @FXML
    private Button SendRequestBackContinueButton;
    @FXML
    protected void onSendRequestBackCancelClick(){
        Stage popup = (Stage) SendRequestBackCancelButton.getScene().getWindow();
        popup.close();
    }
    @FXML
    protected void onSendRequestBackContinueClick() throws IOException {
        Stage popup = (Stage) SendRequestBackCancelButton.getScene().getWindow();
        popup.close();

        Main.changeScene("HomePage.fxml");
    }

}