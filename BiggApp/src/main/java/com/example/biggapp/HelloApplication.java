package com.example.biggapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import com.example.biggapp.Request.APICaller;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("LoginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Break Inviter");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch();
        APICaller caller = new APICaller();

        try {
            System.out.println("Active session: " + caller.checkActiveSessionStatus("test"));
            System.out.println("Login succes: " + caller.login("test", "test11"));
            System.out.println("Active session: " + caller.checkActiveSessionStatus("test"));
            System.out.println("Logout succes: " + caller.logout("test"));
            System.out.println("Active session: " + caller.checkActiveSessionStatus("test"));
        }
        catch (Exception e){
            System.out.println("Error!");
        }
    }
}