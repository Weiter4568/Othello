package com.example.othello;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Othello extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Othello");
        FXMLLoader fxmlLoader = new FXMLLoader(Othello.class.getResource("startScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
