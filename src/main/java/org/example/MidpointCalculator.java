package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class MidpointCalculator {
    private static final String API_KEY = "AIzaSyB6qZetW1t_tGagT-jN-zTQK_c4OLwnX8M";

    public double[] getGoogleDirections(String startLocation, String endLocation) {
        //TODO: tidy this up -> add to constructor?
        double[] midpointCoordinates = new double[0];
        try {
            String apiUrl = buildDirectionsApiUrl(startLocation, endLocation);
            System.out.println("URL: " + apiUrl);

            String response = sendGetRequest(apiUrl);

            String duration = getDuration(response);
            System.out.println(duration);

            String distance = getDistance(response);
            System.out.println("Distance: " + distance);

            double[] startLocationCoordinates = getCoordinates(response, "start");
            double[] endLocationCoordinates = getCoordinates(response, "end");
            midpointCoordinates = calculateMidpoint(startLocationCoordinates, endLocationCoordinates);
            System.out.println("Midpoint Coordinates: Lat: " + midpointCoordinates[0] + ", Lng: " + midpointCoordinates[1]);

            String midpointAddress = reverseGeocode(midpointCoordinates[0], midpointCoordinates[1]);
            System.out.println("Midpoint Address: " + midpointAddress);
            return midpointCoordinates;
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO: tidy this up -> error logging
        return midpointCoordinates;
    }

    private String buildDirectionsApiUrl(String startLocation, String endLocation) {
        return "https://maps.googleapis.com/maps/api/directions/json?destination=" +
                endLocation +
                "&mode=transit&origin=" +
                startLocation +
                "&key=" +
                API_KEY;
    }

    private String sendGetRequest(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            return in.lines().collect(Collectors.joining());
        }
    }

    private String getDuration(String jsonString) throws JsonProcessingException {
        JsonNode rootNode = new ObjectMapper().readTree(jsonString);
        JsonNode durationNode = rootNode.get("routes").get(0).get("legs").get(0).get("duration");
        return "The journey is going to take " + durationNode.get("text").asText();
    }

    private String getDistance(String jsonString) throws JsonProcessingException {
        JsonNode rootNode = new ObjectMapper().readTree(jsonString);
        JsonNode distanceNode = rootNode.get("routes").get(0).get("legs").get(0).get("distance");
        return distanceNode.get("text").asText();
    }

    private double[] getCoordinates(String jsonString, String type) throws JsonProcessingException {
        JsonNode rootNode = new ObjectMapper().readTree(jsonString);
        JsonNode locationNode = rootNode.get("routes").get(0).get("legs").get(0).get(type + "_location");
        double lat = locationNode.get("lat").asDouble();
        double lng = locationNode.get("lng").asDouble();
        return new double[]{lat, lng};
    }

    private double[] calculateMidpoint(double[] startCoordinates, double[] endCoordinates) {
        double midpointLat = (startCoordinates[0] + endCoordinates[0]) / 2;
        double midpointLng = (startCoordinates[1] + endCoordinates[1]) / 2;
        return new double[]{midpointLat, midpointLng};
    }

    private String reverseGeocode(double latitude, double longitude) throws IOException {
        String apiUrl = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&key=" + API_KEY;
        String response = sendGetRequest(apiUrl);
        JsonNode jsonNode = new ObjectMapper().readTree(response);
        return jsonNode.get("results").get(0).get("formatted_address").asText();
    }
}
