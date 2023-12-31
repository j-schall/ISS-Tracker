package org.jan.isstracker.UI;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.controlsfx.control.WorldMapView;

public class WorldMap extends WorldMapView {
    public WorldMap() {
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