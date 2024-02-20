package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.objects.XNumber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GoogleDirectionsApi {
    private static final String API_KEY = "AIzaSyB6qZetW1t_tGagT-jN-zTQK_c4OLwnX8M";

    public void getGoogleDirections(String startLocation, String endLocation) {
        try {
            // API URL
            String apiUrl = "https://maps.googleapis.com/maps/api/directions/json?destination=" + endLocation + "&mode=transit&origin=" + startLocation + "&key=" + API_KEY;
            System.out.println("url is : " + apiUrl);
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Create BufferedReader to read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //get the duration

                String duration = getDuration(response.toString());

                System.out.println(duration);
                System.out.println(getDistance(response.toString()));
                System.out.println(calculateMidWayPointInDistance(getDistance(response.toString())));


            } else {
                System.out.println("Error: " + responseCode);
            }
            connection.disconnect();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    private String getDuration(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonString);
        JsonNode root = rootNode.get("routes").get(0).get("legs");
        JsonNode legsArray = root.get(0);
        JsonNode durationObject = legsArray.get("duration");
        String durationValue = durationObject.get("text").toString();
        return "The journey is going to take " + durationValue;
    }

    private String getDistance(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonString);
        JsonNode root = rootNode.get("routes").get(0).get("legs");
        JsonNode legsArray = root.get(0);
        JsonNode durationObject = legsArray.get("distance");
        String durationValue = durationObject.get("text").toString();
        System.out.println("the distance is "+durationValue);
        return durationValue;
    }

    private int calculateMidWayPointInDistance(String fullDurationValue) {
        // Remove any non-numeric characters from the string
        String numericString = fullDurationValue.replaceAll("[^\\d.]", "");
        double distanceDouble = Double.parseDouble(numericString);
        return (int) distanceDouble / 2;
    }
}
