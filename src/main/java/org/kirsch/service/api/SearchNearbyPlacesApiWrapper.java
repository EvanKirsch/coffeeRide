package org.kirsch.service.api;

import com.google.api.gax.rpc.FixedHeaderProvider;
import com.google.api.gax.rpc.HeaderProvider;
import com.google.maps.places.v1.Circle;
import com.google.maps.places.v1.Place;
import com.google.maps.places.v1.PlacesClient;
import com.google.maps.places.v1.PlacesSettings;
import com.google.maps.places.v1.SearchNearbyRequest;
import com.google.maps.places.v1.SearchNearbyResponse;
import com.google.type.LatLng;
import java.util.ArrayList;
import java.util.List;
import org.kirsch.util.ApplicationProperties;
import org.kirsch.util.DistanceCalculator;
import org.springframework.stereotype.Service;

@Service
public class SearchNearbyPlacesApiWrapper implements ISearchNearbyPlacesApiWrapper {

  private static final String FIELD_MASK_HEADER = "X-Goog-FieldMask";
  private static final String FIELD_MASK_VALUE = "*";

  private String apiKey = ApplicationProperties.getInstance().getGoogleApiKey();

  @Override
  public List<Place> searchNearby(LatLng origin, LatLng destination) {
    List<Place> places = new ArrayList<>();

    try {

      HeaderProvider headerProvider = FixedHeaderProvider.create(
          FIELD_MASK_HEADER, FIELD_MASK_VALUE
      );

      PlacesSettings placesSettings = PlacesSettings.newBuilder()
          .setApiKey(apiKey)
          .setHeaderProvider(headerProvider)
          .build();

      try (PlacesClient placesClient = PlacesClient.create(placesSettings)) {

        SearchNearbyRequest.LocationRestriction restriction = getLocationRestriction(origin, destination);

        SearchNearbyRequest request = SearchNearbyRequest.newBuilder()
            .addIncludedTypes("coffee_shop")
            .setLocationRestriction(restriction)
            .setMaxResultCount(ApplicationProperties.getInstance().getPlacesMaxResultCount())
            .build();

        SearchNearbyResponse searchNearbyResponse = placesClient.searchNearby(request);

        if (searchNearbyResponse.isInitialized()) {
          places = searchNearbyResponse.getPlacesList();
        } else {
          System.out.println("No routes found.");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return places;
  }

  SearchNearbyRequest.LocationRestriction getLocationRestriction(LatLng origin, LatLng destination) {

    Circle searchArea = Circle.newBuilder()
        .setCenter(destination)
        .setRadius(DistanceCalculator.approxCrowDistance(origin, destination))
        .build();

    return SearchNearbyRequest.LocationRestriction
        .newBuilder()
        .setCircle(searchArea)
        .build();

  }

}