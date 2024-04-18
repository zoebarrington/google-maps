package com.example.controller;

import com.example.MidpointCalculator;
import com.example.RestaurantsSearch;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class AppController {

    private RestaurantsSearch restaurantsSearch = new RestaurantsSearch();

    @GetMapping("/restaurants")
    public String getRestaurants() throws JsonProcessingException {
        String midpointLocation = testing();
        Map<String, String> nearbyRestaurants = restaurantsSearch.getListOfNearbyRestaurants(midpointLocation);
        StringBuilder result = new StringBuilder();
        nearbyRestaurants.forEach((key, value) -> result.append(key).append(": ").append(value).append("\n"));
        System.out.println(result.toString());
        return result.toString();
    }
    private String testing() {
        MidpointCalculator midpointCalculator = new MidpointCalculator();
         double[] midpointAddress = midpointCalculator.getGoogleDirections("e35pd", "sw197qu");
        RestaurantsSearch restaurantsSearch = new RestaurantsSearch();
       String midpoint = restaurantsSearch.getGooglePlaces(midpointAddress[0], midpointAddress[1]);
       return midpoint;
    }
}



//
//@RestController
//@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:3000")
//public class AppController {
//
//    private final RestTemplate restTemplate;
//    private final ObjectMapper objectMapper;
//
//    private final RestaurantsSearch restaurantsSearch;
//
//    @Autowired
//    public AppController(RestTemplate restTemplate, ObjectMapper objectMapper, RestaurantsSearch restaurantsSearch) {
//        this.restTemplate = restTemplate;
//        this.objectMapper = objectMapper;
//        this.restaurantsSearch = restaurantsSearch;
//    }
//
//    @GetMapping("/restaurants")
//    public ResponseEntity<List<RestaurantsResponseDto>> getRestaurants(String midpointLocation) throws JsonProcessingException {
//        // Make HTTP request to the backend API
//        String responseBody = restaurantsSearch.getListOfNearbyRestaurants(midpointLocation).toString();
////        String responseBody = restTemplate.getForObject("http://localhost:8080/api/restaurants?midpointLocation=" + midpointLocation, String.class);
//        System.out.println("response body " + responseBody);
//        // Parse the JSON response
//        JsonNode rootNode = objectMapper.readTree(responseBody);
//        List<RestaurantsResponseDto> responseDtoList = new ArrayList<>();
//
//        // Extract data from JSON and create RestaurantsResponseDto objects
//        Iterator<JsonNode> restaurantsIterator = rootNode.elements();
//        while (restaurantsIterator.hasNext()) {
//            JsonNode restaurantNode = restaurantsIterator.next();
//            String name = restaurantNode.get("name").asText();
//            String website = restaurantNode.get("website").asText();
//
//            // Create RestaurantsResponseDto object and add it to the list
//            RestaurantsResponseDto responseDto = new RestaurantsResponseDto(name, website);
//            responseDtoList.add(responseDto);
//        }
//
//        // Return the list of RestaurantsResponseDto objects
//        System.out.println(ResponseEntity.ok(responseDtoList));
//        return ResponseEntity.ok(responseDtoList);
//    }
//}

