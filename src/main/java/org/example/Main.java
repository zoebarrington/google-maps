package org.example;

public class Main {
    public static void main(String[] args) {
    MidpointCalculator midpointCalculator = new MidpointCalculator();
    ClosestTubeCalculator closestTubeCalculator = new ClosestTubeCalculator();
    double[] midpointCoordinates = midpointCalculator.getGoogleDirections("e35pd", "sw197qu");
    String mapsUrl = closestTubeCalculator.buildNearbyTubeUrl(midpointCoordinates[0], midpointCoordinates[1], "1000");
        System.out.println(mapsUrl);
    }
}
