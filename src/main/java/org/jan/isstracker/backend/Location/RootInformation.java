package org.jan.isstracker.backend.Location;

public class RootInformation {
    public long timestamp;
    public String message;
    public ISSPosition iss_position;

    @Override
    public String toString() {
        return "RootInformation{" +
                "timestamp=" + timestamp +
                ", message='" + message + '\'' +
                ", iss_position=" + iss_position +
                '}';
    }
}
