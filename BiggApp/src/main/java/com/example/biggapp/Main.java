package com.example.biggapp;

import com.example.biggapp.Request.APICaller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.example.biggapp.ServerCommunication.*;

public class Main extends Application {
    public static Stage primaryStage;
    public static String credentials = "credentials.txt";

    @Override
    public void start(Stage stage) throws IOException {

        String username = "";

        try {
            String creds = Files.readString(Paths.get(credentials), StandardCharsets.US_ASCII);
            username = creds.split(" ")[0];
        }
        catch (Exception e){
            username = "";
        }

        APICaller apiCaller = new APICaller();
        boolean succes = false;

        try{
            succes = apiCaller.checkActiveSessionStatus(username);
        }
        catch (Exception ignore){

        }

        if (succes){
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("HomePage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
            stage.setTitle("Break Inviter");
            stage.setScene(scene);
            stage.show();
            primaryStage = stage;
        }
        else{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LoginPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
            stage.setTitle("Break Inviter");
            stage.setScene(scene);
            stage.show();
            primaryStage = stage;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LoginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Break Inviter");
        stage.setScene(scene);
        stage.show();
        primaryStage = stage;
    }

    public static void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(Main.class.getResource(fxml));

        Scene scene = new Scene( pane );
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
        try {
            ClientSocket client = new ClientSocket();
            client.test();
        }
        catch (Exception e){
            System.out.println(e.getLocalizedMessage());
            System.exit(-1);
        }
    }
}