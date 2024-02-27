package org.example;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

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

    public void getClosestTubeTesting() throws IOException {
        // Define the endpoint URL
        String endpointUrl = "https://places.googleapis.com/v1/places:searchNearby";

        // Define the JSON request body with the fieldMask parameter
        String requestBody = "{\n" +
                "  \"includedTypes\": [\"restaurant\"],\n" +
                "  \"maxResultCount\": 10,\n" +
                "  \"locationRestriction\": {\n" +
                "    \"circle\": {\n" +
                "      \"center\": {\n" +
                "        \"latitude\": 37.7937,\n" +
                "        \"longitude\": -122.3965\n" +
                "      },\n" +
                "      \"radius\": 500.0\n" +
                "    }\n" +
                "  },\n" +
                "  \"fieldMask\": \"*\n" +
                "}";

        // Create an HttpClient
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create an HttpPost with the endpoint URL
            HttpPost httpPost = new HttpPost(endpointUrl);

            // Add headers
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.addHeader("X-Goog-Api-Key", API_KEY);

            // Set the request body
            StringEntity requestEntity = new StringEntity(requestBody);
            httpPost.setEntity(requestEntity);

            // Execute the request
            HttpResponse response = httpClient.execute(httpPost);

            // Get the response entity
            HttpEntity entity = response.getEntity();

            // Print the response
            if (entity != null) {
                String responseString = EntityUtils.toString(entity);
                System.out.println(responseString);
            }
        }
    }

}


