package org.jan.isstracker.UI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
}
