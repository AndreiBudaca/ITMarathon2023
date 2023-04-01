package com.example.biggapp;

import com.example.biggapp.Request.APICaller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    protected void onAddPersonClick() throws IOException, InterruptedException {
        //Create pop-up
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner((Stage) SendRequestBackButton.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SendRequestUserListPopUp.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 800);
        dialog.setScene(scene);
        dialog.show();

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

    @FXML
    protected void updateInviteList(){
        InvitedPersonsTextArea.setText(invitedUsers);
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

    //send request user List pop-up
    @FXML
    private AnchorPane UserListVerticalContainer;
    @FXML
    private VBox ListContainer;
    @FXML
    private Button SendRequestUserListPopUpButton;
    @FXML
    private Button refreshListButton;

    @FXML
    protected void onSendRequestUserListPopUpClick(){
        List<String> selectedUsers = new ArrayList<>();
        for(Node child: ListContainer.getChildren()){
            HBox entry = (HBox) child;
            String username = ((Label)entry.getChildren().get(0)).getText();
            Boolean selected = ((CheckBox)entry.getChildren().get(1)).isSelected();

            if(selected)
                selectedUsers.add(username);
        }

        updateInvitedUsers(selectedUsers);

        Stage popup = (Stage) ListContainer.getScene().getWindow();
        popup.close();
    }

    static String invitedUsers;
    void updateInvitedUsers(List<String> users){
        System.out.println("Inside func list: " + users);
        String s = "";
        for(String user: users)
            s += user + ", ";

        invitedUsers = s;
    }

    @FXML
    protected void onRefreshListClick() throws FileNotFoundException {

        //TODO: send request to server for userlist
        List<String> users = List.of("Mircea Vasile", "Costica Ionel", "Vasile Gheorghe");

        ListContainer.getChildren().clear();

        for(String user: users) {
            HBox newEntry = new HBox();
            newEntry.setSpacing(50);

            Label name = new Label(); name.setText(user);
            CheckBox selectUser = new CheckBox();
            ImageView star = new ImageView();
            star.setFitHeight(32);
            star.setFitWidth(32);
            FileInputStream inputstream = new FileInputStream("/home/danu/Documents/ITMarathon/BiggApp/src/main/resources/Images/FullStar.png");
            Image image = new Image(inputstream);
            star.setImage(image);

            newEntry.getChildren().add(name);
            newEntry.getChildren().add(selectUser);

            //only add image if favorite
            //newEntry.getChildren().add(star);

            ListContainer.getChildren().add(newEntry);


        }
    }

}