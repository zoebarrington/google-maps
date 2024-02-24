package org.example;

public class ClosestTubeCalculator {
    private static final String API_KEY = "AIzaSyB6qZetW1t_tGagT-jN-zTQK_c4OLwnX8M";

    public String buildNearbyTubeUrl(double latitude, double longitude, String radius) {
        String formattedLatitude = String.format("%.6f", latitude);
        String formattedLongitude = String.format("%.6f", longitude);
        return " https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                formattedLatitude +
                "," +
                formattedLongitude +
                "&radius=" +
                radius +
                "&type=subway_station&key=" +
                API_KEY;
    }
}
