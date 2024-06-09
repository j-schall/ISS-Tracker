package org.jan.isstracker.UI;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.controlsfx.control.WorldMapView;

import java.util.Locale;

public class WorldMap extends WorldMapView {
    public static final String ISS_TRACK = "issTrack";
    public static final String ISS_POSITION = "issPosition";
    public WorldMap(String locationType) {
        switch (locationType) {
            case ISS_TRACK -> setIssTrack();
            case ISS_POSITION -> setIssPosition();
        }
    }

    public void setIssPosition() {
        setLocationViewFactory(location -> {
            Circle circle = new Circle();
            circle.setRadius(10);
            circle.setFill(Color.RED);
            circle.setTranslateX(-4);
            circle.setTranslateY(-4);
            return circle;
        });
    }

    public void setIssTrack() {
        setLocationViewFactory(loc -> {
            Rectangle rec = new Rectangle(20, 10);
            rec.setFill(Color.WHITE);
            //circle.setTranslateX(-4);
            //circle.setTranslateY(-4);
            return rec;
        });
    }
}