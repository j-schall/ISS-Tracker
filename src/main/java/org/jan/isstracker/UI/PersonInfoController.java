package org.jan.isstracker.UI;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PersonInfoController {
    @FXML
    private Label agencyLabel;

    @FXML
    private Label countryLabel;

    @FXML
    private Label disLabel;

    @FXML
    private Label idLabel;

    @FXML
    private Label positionLabel;

    public Label getAgencyLabel() {
        return agencyLabel;
    }

    public Label getCountryLabel() {
        return countryLabel;
    }

    public Label getDisLabel() {
        return disLabel;
    }

    public Label getIdLabel() {
        return idLabel;
    }

    public Label getPositionLabel() {
        return positionLabel;
    }
    private Stage closeStage;

    public void setCloseScene(Stage stage) {
        closeStage = stage;
    }

    @FXML
    void exit(MouseEvent event) {
        if (closeStage == null)
            System.out.println("No closing stage was specified!");
        closeStage.close();
    }
}
