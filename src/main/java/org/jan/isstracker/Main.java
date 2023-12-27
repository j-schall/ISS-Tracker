package org.jan.isstracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jan.isstracker.UI.View;

public class Main extends Application {
    public static Stage WINDOW;
    @Override
    public void start(Stage stage) throws Exception {
        WINDOW = stage;
        FXMLLoader loader = new FXMLLoader(View.class.getResource("/View.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        stage.setTitle("ISS-Tracker");
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
