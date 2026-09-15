package com.clase;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/com/clase/primary.fxml")
        );
            Scene scene = new Scene(loader.load(), 300, 200);
            stage.setTitle("Controles Básicos");
            stage.setScene(scene);
            stage.show();
    }
    public static void main(String[] args) {
        launch();
    }

}