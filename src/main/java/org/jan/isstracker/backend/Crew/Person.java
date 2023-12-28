package org.jan.isstracker.backend.Crew;

public class Person {
    public int id;
    public String name;
    public String country;
    public String flag_code;
    public String agency;
    public String position;
    public String spacecraft;
    public int launched;
    public boolean iss;
    public int days_in_space;
    public String url;
    public String image;
    public String instagram;
    public String twitter;
    public String facebook;

    public String getName() {
        return name;
    }

    public String getSpacecraft() {
        return spacecraft;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", flag_code='" + flag_code + '\'' +
                ", agency='" + agency + '\'' +
                ", position='" + position + '\'' +
                ", spacecraft='" + spacecraft + '\'' +
                ", launched=" + launched +
                ", iss=" + iss +
                ", days_in_space=" + days_in_space +
                ", url='" + url + '\'' +
                ", image='" + image + '\'' +
                ", instagram='" + instagram + '\'' +
                ", twitter='" + twitter + '\'' +
                ", facebook='" + facebook + '\'' +
                '}';
    }

}
