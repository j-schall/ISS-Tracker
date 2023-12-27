package org.jan.isstracker.backend.Location;

public class ISSPosition {
    public String longitude;
    public String latitude;

    @Override
    public String toString() {
        return "ISSPosition{" +
                "longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
