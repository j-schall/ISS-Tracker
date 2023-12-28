package org.jan.isstracker.backend;

import com.google.gson.Gson;
import org.jan.isstracker.backend.Crew.CrewInformation;
import org.jan.isstracker.backend.Location.RootInformation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIRequest {
    public static final String CREW = "crew";
    public static final String LOCATION = "location";

    public String getLocation() {
        try {
            URL url = new URL("http://api.open-notify.org/iss-now.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            int status = conn.getResponseCode();

            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                StringBuilder content = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
                reader.close();

                // Convert JSON to Java Classes (ISSPosition and RootInformation)
                return content.toString();
            }
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCrew() {
        try {
            URL url = new URL("https://corquaid.github.io/international-space-station-APIs/JSON/people-in-space.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);

            int status = conn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                StringBuilder content = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
                reader.close();
                return content.toString();
            }
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T convertToClass(String selection) {
        Gson gson = new Gson();
        CrewInformation crewInformation;
        RootInformation info;

        switch (selection) {
            case CREW -> {
                crewInformation = gson.fromJson(getCrew(), CrewInformation.class);
                return (T) crewInformation;
            }
            case LOCATION -> {
                info = gson.fromJson(getLocation(), RootInformation.class);
                return (T) info;
            }
            default -> throw new IllegalStateException("Unexpected value: " + selection);
        }
    }
}
