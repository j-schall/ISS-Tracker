package org.jan.isstracker.backend.Crew;

import java.util.ArrayList;

public class CrewInformation {
    public int number;
    public int iss_expedition;
    public String expedition_patch;
    public String expedition_url;
    public String expedition_image;
    public int expedition_start_date;
    public int expedition_end_date;
    public ArrayList<Person> people;

    @Override
    public String toString() {
        return "CrewInformation{" +
                "number=" + number +
                ", iss_expedition=" + iss_expedition +
                ", expedition_patch='" + expedition_patch + '\'' +
                ", expedition_url='" + expedition_url + '\'' +
                ", expedition_image='" + expedition_image + '\'' +
                ", expedition_start_date=" + expedition_start_date +
                ", expedition_end_date=" + expedition_end_date +
                ", people=" + people +
                '}';
    }
}
