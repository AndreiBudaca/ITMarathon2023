package com.example.biggapp;

import com.example.biggapp.Model.Request;
import com.example.biggapp.Model.RequestPerson;
import com.example.biggapp.Request.APICaller;
import com.example.biggapp.ServerCommunication.ClientSocket;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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

    private final APICaller apiCaller = new APICaller();
    private ClientSocket socket = null;
    private int userID = 0;
    private String username;

    public AppController() {
        try {
            socket = new ClientSocket();
        }
        catch (Exception e){
            System.err.println("Error opening server socket!\n" + e.getLocalizedMessage());
            System.exit(-1);
        }
    }

    @FXML
    protected void onLoginClick() throws IOException {
        username = LoginUsernameTextField.getText();
        String password = LoginPasswordTextField.getText();

        //TODO send request with user and pass
        FileWriter writer = new FileWriter("credentials.txt");

        writer.write(username + " " + password);
        writer.flush();
        writer.close();

        boolean succes = false;

        try{
            succes = apiCaller.login(username, password);
        }
        catch (Exception ignore){

        }

        //if successful, redirect to homepage:
        if(succes) {
            userID = socket.getId(username);
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
    protected void onHomeSearchBarUpdated() throws IOException {
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
        }else{
            try {
                String lastSearches = new String(Files.readAllBytes(Paths.get("LastSearches.txt")));

                if(!lastSearches.isBlank()){
                    List<String> results = new ArrayList<>(List.of(lastSearches.split("\n")));
                    results.remove("");
                    validUsers.addAll(results);
                }
            }catch (Exception ignore){

            }
        }

        //create and display valid results
        for(String result: validUsers){
            Button resultLabel = new Button();
            resultLabel.setText(result);

            resultLabel.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        try {
                            Files.write(Paths.get("BiggApp/LastSearches.txt"), ("\n" + result).getBytes(), StandardOpenOption.APPEND);
                        }catch (IOException e) {
                        }
                        Main.changeScene("ProfilePage.fxml");

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            SearchResultsVBox.getChildren().add(resultLabel);
        }

    }
    static List<Request> requests = new ArrayList<>();
    @FXML
    protected void onMyRequestsClick() throws IOException {
        Main.changeScene("MyRequestsPage.fxml");
    }

    @FXML
    protected void onSendRequestClick() throws IOException {
        Main.changeScene("SendRequestPage.fxml");
    }
    @FXML
    protected void onLogoutClick() throws IOException {
        //if successful, redirect to homepage:
        String creds = "";
        try {
            creds = Files.readString(Paths.get("credentials.txt"), StandardCharsets.US_ASCII);
        }
        catch (Exception ignore){
        }

        if (apiCaller.logout(creds.split(" ")[0]))
            Main.changeScene("LoginPage.fxml");
    }
    @FXML
    protected void onMyProfileClick() throws IOException {
        Main.changeScene("ProfilePage.fxml");
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
    private Label MissingReqFieldLabel;

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
    protected void onSendRequestSendClick() throws IOException {
        //get data and send request
        List<String> users;

        if (!InvitedPersonsTextArea.getText().isEmpty()) {

            users = List.of(InvitedPersonsTextArea.getText().split(", "));
        }
        else
            users = new ArrayList<>(0);

        String location = LocationTextField.getText();
        String comment = CommentTextArea.getText();

        //if all fields are good, send request
        if(!users.isEmpty() && !location.isBlank()) {
            System.out.println("Test");
            // Get id's
            List<RequestPerson> receivers = new ArrayList<RequestPerson>(0);
            for (String fullName: users){
                // Get user id
                int id = new ClientSocket().getPersonID(fullName.split(" ")[0], fullName.split(" ")[1]);
                receivers.add(new RequestPerson(
                        id,
                        0
                ));
            }

            Request req = new Request(
                    userID,
                    receivers,
                    location,
                    comment
            );

            new ClientSocket().putReqest(req);

            Main.changeScene("HomePage.fxml");
        }
        else{
            MissingReqFieldLabel.setVisible(true);
        }

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
            //FileInputStream inputstream = new FileInputStream("/home/danu/Documents/ITMarathon/BiggApp/src/main/resources/Images/FullStar.png");
            //Image image = new Image(inputstream);
            //star.setImage(image);

            newEntry.getChildren().add(name);
            newEntry.getChildren().add(selectUser);

            //only add image if favorite
            //newEntry.getChildren().add(star);

            ListContainer.getChildren().add(newEntry);


        }
    }

    //Profile page
    @FXML
    private Button ProfileBackButton;
    @FXML
    private Button SaveProfileButton;
    @FXML
    private Button UpdateProfileButton;
    @FXML
    private TextField profileNameTextField;
    @FXML
    private TextField profileDepartmentTextField;
    @FXML
    private TextField profileOfficeTextField;
    @FXML
    private TextField profileTeamTextField;
    @FXML
    private TextField profileFloorTextField;
    @FXML
    private TextField profileFromTextField;
    @FXML
    private TextField profileToTextField;

    @FXML
    protected void onProfileBackClick() throws IOException {
        Main.changeScene("HomePage.fxml");
    }

    @FXML
    protected void onUpdateClick(){
        profileNameTextField.setDisable(false);
        profileDepartmentTextField.setDisable(false);
        profileOfficeTextField.setDisable(false);
        profileTeamTextField.setDisable(false);
        profileFloorTextField.setDisable(false);
        profileFromTextField.setDisable(false);
        profileToTextField.setDisable(false);
    }

    @FXML
    protected void onSaveBackClick(){
        profileNameTextField.setDisable(true);
        profileDepartmentTextField.setDisable(true);
        profileOfficeTextField.setDisable(true);
        profileTeamTextField.setDisable(true);
        profileFloorTextField.setDisable(true);
        profileFromTextField.setDisable(true);
        profileToTextField.setDisable(true);
    }

    //my requests page
    @FXML
    private Button MyRequestsBackButton;
    @FXML
    private VBox requestContainer;

    @FXML
    protected void onMyRequestsBackClick() throws IOException {
        Main.changeScene("HomePage.fxml");
    }

    @FXML
    protected void updateMyRequests(){
        requestContainer.getChildren().clear();

        //TODO: get requests from server
        RequestPerson person1 = new RequestPerson(1);
        RequestPerson person2 = new RequestPerson(2);
        RequestPerson person3 = new RequestPerson(3);
        Request req1 = new Request(1, List.of(person1, person2, person3), "Parcare", "Aduceti seminte");

        RequestPerson person4 = new RequestPerson(4);
        RequestPerson person5 = new RequestPerson(5);
        Request req2 = new Request(2, List.of(person1, person4, person5), "Etajul 3", "Se pune muzica");

        List<Request> requests = List.of(req1, req2);

        for(Request req: requests){
            HBox entry = new HBox();
            entry.setSpacing(30);

            List<String> receiverNames = new ArrayList<>();
            for(RequestPerson receiver: req.getReceivers())
                receiverNames.add(String.valueOf(receiver.getId()));


            Label intiator = new Label("Intiator: " + req.getSenderId());
            Label receivers = new Label("Invited: " + receiverNames);
            Label place = new Label("Place: " + req.getLocation());
            Label comment = new Label("Comment: " + req.getComment());
            Button accept = new Button("Accept");
            Button decline = new Button("Decline");

            accept.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    //send request saying "accept"
                }
            });

            decline.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    //send request saying "decline"
                }
            });

            entry.getChildren().add(intiator);
            entry.getChildren().add(receivers);
            entry.getChildren().add(place);
            entry.getChildren().add(comment);
            entry.getChildren().add(accept);
            entry.getChildren().add(decline);

            requestContainer.getChildren().add(entry);

        }
    }
}