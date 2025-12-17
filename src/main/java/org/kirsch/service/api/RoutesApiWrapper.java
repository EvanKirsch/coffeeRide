package org.kirsch.service.api;

import com.google.api.gax.rpc.FixedHeaderProvider;
import com.google.api.gax.rpc.HeaderProvider;
import com.google.maps.routing.v2.ComputeRoutesRequest;
import com.google.maps.routing.v2.Location;
import com.google.maps.routing.v2.Route;
import com.google.maps.routing.v2.RouteTravelMode;
import com.google.maps.routing.v2.RoutesClient;
import com.google.maps.routing.v2.RoutesSettings;
import com.google.maps.routing.v2.Waypoint;
import com.google.type.LatLng;
import java.util.List;
import org.kirsch.util.ApplicationProperties;
import org.springframework.stereotype.Service;

@Service
public class RoutesApiWrapper implements IRoutesApiWrapper {

  private static final String FIELD_MASK_HEADER = "X-Goog-FieldMask";
  private static final String FIELD_MASK_VALUE = "routes.duration,routes.distanceMeters";

  public void doGet(LatLng origin, LatLng destination) {

    String apiKey = ApplicationProperties.getInstance().getGoogleApiKey();

    try {

      HeaderProvider headerProvider = FixedHeaderProvider.create(
          FIELD_MASK_HEADER, FIELD_MASK_VALUE
      );

      RoutesSettings routesSettings = RoutesSettings.newBuilder()
          .setApiKey(apiKey)
          .setHeaderProvider(headerProvider)
          .build();

      try (RoutesClient routesClient = RoutesClient.create(routesSettings)) {


        ComputeRoutesRequest request = ComputeRoutesRequest.newBuilder()
            .setOrigin(createWaypointForLatLng(origin))
            .setDestination(createWaypointForLatLng(destination))
            .setTravelMode(RouteTravelMode.BICYCLE)
            .build();

        List<Route> responseRoutes = routesClient.computeRoutes(request).getRoutesList();

        if (!responseRoutes.isEmpty()) {
          Route route = responseRoutes.get(0);
          System.out.println("Route found:");
          System.out.println("Duration: " + route.getDuration());
          System.out.println("Distance Meters: " + route.getDistanceMeters());
        } else {
          System.out.println("No routes found.");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  Waypoint createWaypointForLatLng(LatLng latLng) {
    return Waypoint.newBuilder()
        .setLocation(Location.newBuilder()
            .setLatLng(latLng)
            .build())
        .build();
  }

}