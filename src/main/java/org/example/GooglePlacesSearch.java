package org.example;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class GooglePlacesSearch {
    private static final String API_KEY = "AIzaSyB6qZetW1t_tGagT-jN-zTQK_c4OLwnX8M";

    public void getGooglePlaces(double latitude, double longitude) {
        // API endpoint URL
        String url = "https://places.googleapis.com/v1/places:searchNearby";


        // Request body
        String requestBody = "{" +
                "\"includedTypes\": [\"restaurant\"]," +
                "\"maxResultCount\": 10," +
                "\"locationRestriction\": {" +
                "\"circle\": {" +
                "\"center\": {" +
                "\"latitude\":"+latitude+ "," +
                "\"longitude\":"+longitude+
                "}," +
                "\"radius\": 500.0" +
                "}" +
                "}" +
                "}";

        // Request headers
        String contentType = "application/json";
        String fieldMask = "places.displayName";

        try {
            // Create an HttpClient instance
            HttpClient httpClient = HttpClients.createDefault();

            // Create an HttpPost object with the endpoint URL
            HttpPost httpPost = new HttpPost(url);

            // Set request headers
            httpPost.setHeader("Content-Type", contentType);
            httpPost.setHeader("X-Goog-Api-Key", API_KEY);
            httpPost.setHeader("X-Goog-FieldMask", fieldMask);

            // Set request body
            StringEntity requestEntity = new StringEntity(requestBody);
            httpPost.setEntity(requestEntity);

            // Execute the request and obtain the response
            HttpResponse response = httpClient.execute(httpPost);

            // Extract response body
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);

            // Print response body
            System.out.println("Response Body:");
            System.out.println(responseBody);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
