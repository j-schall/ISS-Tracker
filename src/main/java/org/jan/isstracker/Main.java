/*
██╗███████╗███████╗   ████████╗██████╗  █████╗  ██████╗██╗  ██╗███████╗██████╗
██║██╔════╝██╔════╝   ╚══██╔══╝██╔══██╗██╔══██╗██╔════╝██║ ██╔╝██╔════╝██╔══██╗
██║███████╗███████╗█████╗██║   ██████╔╝███████║██║     █████╔╝ █████╗  ██████╔╝
██║╚════██║╚════██║╚════╝██║   ██╔══██╗██╔══██║██║     ██╔═██╗ ██╔══╝  ██╔══██╗
██║███████║███████║      ██║   ██║  ██║██║  ██║╚██████╗██║  ██╗███████╗██║  ██║
╚═╝╚══════╝╚══════╝      ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝
*/

package org.jan.isstracker;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jan.isstracker.UI.LoadingScreenController;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Main extends Application {
    public static Stage WINDOW;
    private final Pane rootPane = new Pane();
    private boolean loadedMainView = false;
    private boolean loadMainViewAlreadyFailed = false;

    @Override
    public void start(Stage stage) throws Exception {
        WINDOW = stage;
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/LoadingScreen.fxml"));
        rootPane.getChildren().add(loader.load());
        Scene scene = new Scene(rootPane);
        scene.getStylesheets().add(String.valueOf(Main.class.getResource("/css/tableViewStyle.css")));
        stage.setScene(scene);
        stage.setTitle("ISS-Tracker");
        stage.setResizable(false);
        stage.show();

        Timeline timeline = getTimeline(loader);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private Timeline getTimeline(FXMLLoader loader) {
        AtomicInteger failureCounter = new AtomicInteger(); // Increases after each connection failure

        // An Internet connection is searched for after every 2 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            if (!loadedMainView) {
                if (netIsAvailable())
                    loadMainView();
                failureCounter.getAndIncrement();
            }

            // If after 20 seconds, no stable internet connection was found, an error message
            // will be shown on the loading screen.
            if (failureCounter.get() >= 10) {
                LoadingScreenController controller = loader.getController();
                Label connectionFailureLb = controller.getInternetConnFailureLabel();
                connectionFailureLb.setText("Couldn't found a stable Internet connection. Please retry.");
            }
        }));
        return timeline;
    }

    private void loadMainView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View.fxml"));
            rootPane.getChildren().clear();
            rootPane.getChildren().add(loader.load());
            loadedMainView = true;
        } catch (IllegalStateException | IOException e) {
            if (!loadMainViewAlreadyFailed) {
                e.printStackTrace();
                Alert loadingErrorAlert = new Alert(Alert.AlertType.ERROR);
                loadingErrorAlert.setTitle("Loading error");
                loadingErrorAlert.setContentText("Couldn't find resources for the main window. Please prove your installation!");
                loadingErrorAlert.initOwner(WINDOW.getOwner());
                loadingErrorAlert.show();
                WINDOW.close();
                loadMainViewAlreadyFailed = true;
            }
        }
    }

    public static boolean netIsAvailable() {
        boolean isConnected = false;
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            isConnected = true;
        } catch (IOException ignored) {
        }
        return isConnected;
    }

    public static void main(String[] args) {
        launch();
    }
}
