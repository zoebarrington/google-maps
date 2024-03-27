package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
    MidpointCalculator midpointCalculator = new MidpointCalculator();
    double[] midpointAddress = midpointCalculator.getGoogleDirections("e35pd", "sw197qu");

        RestaurantsSearch restaurantsSearch = new RestaurantsSearch();
        restaurantsSearch.getGooglePlaces(midpointAddress[0], midpointAddress[1]);
    }
}
