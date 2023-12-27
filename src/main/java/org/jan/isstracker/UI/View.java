package org.jan.isstracker.UI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.controlsfx.control.WorldMapView;
import org.jan.isstracker.Main;
import org.jan.isstracker.backend.APIRequest;
import org.jan.isstracker.backend.Crew.CrewInformation;
import org.jan.isstracker.backend.Location.RootInformation;

import java.net.URL;
import java.time.Instant;
import java.util.*;

public class View implements Initializable {
    @FXML
    private ImageView connIndicator;

    @FXML
    private Label latitudeLabel;

    @FXML
    private Label longitudeLabel;

    @FXML
    private Label timestampLabel;

    @FXML
    private Pane mapPane;

    private final String RECEIVE_DATA = "success";
    private RootInformation info;
    private CrewInformation crewInfo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        makeRequest();

        // Set Timer, to receive new data
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (checkConnection()) {
                    makeRequest();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Disconnected from server. Please retry.");
                    alert.setTitle("Internet Error");
                    alert.initOwner(Main.WINDOW);
                }

            }
        }, 2000, 2000);
    }

    private void makeRequest() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                // Make API request
                APIRequest request = new APIRequest();
                info = request.convertToClass(APIRequest.LOCATION);
                crewInfo = request.convertToClass(APIRequest.CREW);
                checkConnection();

                double longitude = Double.parseDouble(info.iss_position.longitude);
                double latitude = Double.parseDouble(info.iss_position.latitude);

                // Display the data on the GUI
                latitudeLabel.setText(String.valueOf(latitude));
                longitudeLabel.setText(String.valueOf(longitude));
                setMap(latitude, longitude);
                setTime();
            }
        });
    }

    private void setTime() {
        long timeStamp = info.timestamp;

        // Unix Time convert to our time
        Instant instant = Instant.ofEpochSecond(timeStamp);
        Date date = Date.from(instant);
        timestampLabel.setText(date.toString());
    }

    private boolean checkConnection() {
        if (info.message.equals(RECEIVE_DATA)) {
            connIndicator.setImage(new Image(Objects.requireNonNull(View.class.getResourceAsStream("/connected.png"))));
            return true;
        } else {
            connIndicator.setImage(new Image(Objects.requireNonNull(View.class.getResourceAsStream("/disconnected.png"))));
            return false;
        }
    }

    private void setMap(double latitude, double longitude) {
        WorldMapView mapView = new WorldMapView();
        mapView.setPrefSize(332, 210);
        WorldMapView.Location location = new WorldMapView.Location(latitude, longitude);
        mapView.getLocations().add(location);
        mapPane.getChildren().add(mapView);
    }
}
