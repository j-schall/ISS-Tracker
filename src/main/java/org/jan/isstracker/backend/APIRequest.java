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
    private boolean connectionStatus = false;
    private String latestCrewInfo;
    private String latestIssInfo;
    private String crewData;
    private String issPosData;

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
                issPosData = content.toString();
                connectionStatus = true;
                return issPosData;
            }
            conn.disconnect();
        } catch (IOException ignored) {
            connectionStatus = false;
        }
        latestIssInfo = issPosData;
        System.out.println("Returned backup iss data");
        return latestIssInfo;
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
                // If the program looses an Internet connection, it will show the latest data
                // when an Internet connection was there
                crewData = content.toString();
                connectionStatus = true;
                return crewData;
            }
            conn.disconnect();
        } catch (IOException ignored) {
            connectionStatus = false;
        }
        latestCrewInfo = crewData;
        System.out.println("Returned backup Crew data");
        return latestCrewInfo;
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

    public boolean getConnectionStatus() {
        return connectionStatus;
    }
}
