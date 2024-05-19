package org.jan.isstracker.UI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoadingScreenController {
    @FXML
    private Label internetConnFailureLabel;

    public Label getInternetConnFailureLabel() {
        return internetConnFailureLabel;
    }

    public void setInternetConnFailureLabel(Label internetConnFailureLabel) {
        this.internetConnFailureLabel = internetConnFailureLabel;
    }
}
