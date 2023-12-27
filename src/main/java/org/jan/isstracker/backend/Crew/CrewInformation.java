package org.jan.isstracker.backend.Crew;

import java.util.ArrayList;

public class CrewInformation {
    public String message;
    public ArrayList<Person> people;
    public int number;

    @Override
    public String toString() {
        return "CrewInformation{" +
                "message='" + message + '\'' +
                ", people=" + people +
                ", number=" + number +
                '}';
    }
}
