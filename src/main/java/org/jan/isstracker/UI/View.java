package org.jan.isstracker.UI;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.WorldMapView;
import org.jan.isstracker.Main;
import org.jan.isstracker.backend.APIRequest;
import org.jan.isstracker.backend.Crew.CrewInformation;
import org.jan.isstracker.backend.Crew.Person;
import org.jan.isstracker.backend.Location.RootInformation;

import java.io.IOException;
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

    @FXML
    private Label totalCrewNumber;

    @FXML
    private TableView<Person> memberTable;

    @FXML
    private TableColumn<String, Person> nameColumn;

    @FXML
    private TableColumn<String, Person> craftColumn;

    @FXML
    private ImageView expeditionPatchView;

    @FXML
    private Label expeditionLabel;

    @FXML
    private Label startDateLabel;

    @FXML
    private Label endDateLabel;
    private final String RECEIVE_DATA = "success";
    private RootInformation info;
    private CrewInformation crewInfo;
    private final ArrayList<Person> oldPersonData = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        assert checkConnection();
        makeRequest();
        APIRequest request = new APIRequest();
        getPersonsInSpace(request.convertToClass(APIRequest.CREW));
        try {
            getGeneralInformation(request.convertToClass(APIRequest.CREW));
        } catch (IOException ignored) {}

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

                // set time
                long timeStamp = info.timestamp;
                timestampLabel.setText(setTime(timeStamp));

                // Display the data on the GUI
                latitudeLabel.setText(String.valueOf(latitude));
                longitudeLabel.setText(String.valueOf(longitude));
                setMap(latitude, longitude);
            }
        });
    }

    private String setTime(long timeStamp) {
        // Unix Time convert to our time
        Instant instant = Instant.ofEpochSecond(timeStamp);
        return Date.from(instant).toString();
    }

    private boolean checkConnection() {
        if (info.message.equals(RECEIVE_DATA)) {
            connIndicator.setImage(new Image(Objects.requireNonNull(View.class.getResourceAsStream("/img/connected.png"))));
            return true;
        } else {
            connIndicator.setImage(new Image(Objects.requireNonNull(View.class.getResourceAsStream("/img/disconnected.png"))));
            return false;
        }
    }

    private void setMap(double latitude, double longitude) {
        WorldMap mapView = new WorldMap(WorldMap.ISS_POSITION);
        mapView.setPrefSize(332, 210);
        WorldMap.Location location = new WorldMap.Location(latitude, longitude);
        mapView.getLocations().add(location);
        mapPane.getChildren().add(mapView);
    }

    private void getPersonsInSpace(CrewInformation crewInfo) {
        ArrayList<Person> people = crewInfo.people;

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        craftColumn.setCellValueFactory(new PropertyValueFactory<>("spacecraft"));

        // Map the persons' JSON-data to the tableview
        int count = 1;
        ObservableList<Person> persons = FXCollections.observableArrayList();
        for (Person p : people) {
            if (p.iss) {
                totalCrewNumber.setText(String.valueOf(count));
                persons.add(p);
                count++;
            }
        }
        memberTable.setItems(persons);

        // Ändere die Positionierung des PopOver-Handlers
        memberTable.setRowFactory(tv -> {
            javafx.scene.control.TableRow<Person> row = new javafx.scene.control.TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Person selectedPerson = row.getItem();
                    Stage stage = createPopOver(selectedPerson);
                    stage.show();
                }
            });
            return row;
        });

        oldPersonData.clear();
        oldPersonData.addAll(people);
    }

    private Stage createPopOver(Person selectedPerson) {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle(selectedPerson.name);
        stage.initOwner(Main.WINDOW);

        try {
            FXMLLoader loader = new FXMLLoader(View.class.getResource("/PersonInformation.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

            PersonInfoController infoController = loader.getController();

            Label countryLabel = infoController.getCountryLabel();
            Label agencyLabel = infoController.getAgencyLabel();
            Label disLabel = infoController.getDisLabel();
            Label idLabel = infoController.getIdLabel();
            Label positionLabel = infoController.getPositionLabel();

            countryLabel.setText(selectedPerson.country);
            agencyLabel.setText(selectedPerson.agency);
            disLabel.setText(String.valueOf(selectedPerson.days_in_space));
            idLabel.setText(String.valueOf(selectedPerson.id));
            positionLabel.setText(selectedPerson.position);
        } catch (IOException ignored) {}

        return stage;
    }

    private void getGeneralInformation(CrewInformation crewInfo) throws IOException {
        expeditionLabel.setText(String.valueOf(crewInfo.iss_expedition));
        URL url = new URL(crewInfo.expedition_patch);
        expeditionPatchView.setImage(new Image(url.openStream()));

        startDateLabel.setText(setTime(crewInfo.expedition_start_date));
        endDateLabel.setText(setTime(crewInfo.expedition_end_date));
    }
}
