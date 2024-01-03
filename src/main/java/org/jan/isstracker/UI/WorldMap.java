package org.jan.isstracker.UI;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.controlsfx.control.WorldMapView;

public class WorldMap extends WorldMapView {
    public static final String ISS_TRACK = "issTrack";
    public static final String ISS_POSITION = "issPosition";
    public WorldMap(String locationType) {
        switch (locationType) {
            case ISS_TRACK -> issTrack();
            case ISS_POSITION -> issPosition();
        }
    }

    private void issTrack() {
        setLocationViewFactory(location -> {
            Circle circle = new Circle();
            circle.setRadius(10);
            circle.setFill(Color.WHITE);
            circle.setTranslateX(-4);
            circle.setTranslateY(-4);
            return circle;
        });
    }

    private void issPosition() {
        setLocationViewFactory(location -> {
            Circle circle = new Circle();
            circle.setRadius(15);
            circle.setFill(Color.RED);
            circle.setTranslateX(-4);
            circle.setTranslateY(-4);
            return circle;
        });
    }
}