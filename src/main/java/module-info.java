module org.jan.isstracker {

    // requires JavaFX modules that the application uses
    requires javafx.graphics;
    requires com.google.gson;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;

    exports org.jan.isstracker;
    exports org.jan.isstracker.UI;
    exports org.jan.isstracker.backend;

    opens org.jan.isstracker.UI to javafx.fxml;
    opens org.jan.isstracker.backend.Crew to com.google.gson;

    exports org.jan.isstracker.backend.Location;
    exports org.jan.isstracker.backend.Crew;
}

