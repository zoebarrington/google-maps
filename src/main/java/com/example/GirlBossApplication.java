package com.example;

import com.example.controller.AppController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GirlBossApplication {
    public static void main(String[] args) {
        AppController appController = new AppController();
        appController.getRestaurants();
        SpringApplication.run(GirlBossApplication.class, args);
//    MidpointCalculator midpointCalculator = new MidpointCalculator();
//    double[] midpointAddress = midpointCalculator.getGoogleDirections("e35pd", "sw197qu");
//    AppController appController = new AppController();
//        System.out.println(appController.getRestaurants());
//
//        RestaurantsSearch restaurantsSearch = new RestaurantsSearch();
//        restaurantsSearch.getGooglePlaces(midpointAddress[0], midpointAddress[1]);
    }
}
