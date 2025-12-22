package org.kirsch.service.api;

import com.google.api.gax.rpc.FixedHeaderProvider;
import com.google.api.gax.rpc.HeaderProvider;
import com.google.maps.places.v1.Place;
import com.google.maps.routing.v2.ComputeRoutesRequest;
import com.google.maps.routing.v2.PolylineQuality;
import com.google.maps.routing.v2.Route;
import com.google.maps.routing.v2.RouteModifiers;
import com.google.maps.routing.v2.RouteTravelMode;
import com.google.maps.routing.v2.RoutesClient;
import com.google.maps.routing.v2.RoutesSettings;
import com.google.maps.routing.v2.RoutingPreference;
import com.google.maps.routing.v2.Waypoint;
import com.google.type.LatLng;
import java.util.ArrayList;
import java.util.List;
import org.kirsch.util.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class RoutesApiWrapper implements IRoutesApiWrapper {

  private static final String FIELD_MASK_HEADER = "X-Goog-FieldMask";
  private static final String FIELD_MASK_VALUE = "routes.duration,routes.distanceMeters,routes.polyline,routes.legs";

  private final ConversionService conversionService;

  @Autowired
  public RoutesApiWrapper(ConversionService conversionService) {
    this.conversionService = conversionService;
  }

  @Override
  public List<Route> computeRoute(LatLng origin, LatLng destination, List<Place> intermediates) {
    List<Route> responseRoutes = new ArrayList<>();
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

        List<Waypoint> waypoints = intermediates.stream()
            .map(elt -> conversionService.convert(elt.getLocation(), Waypoint.class))
            .toList();

        ComputeRoutesRequest request = ComputeRoutesRequest.newBuilder()
            .setOrigin(conversionService.convert(origin, Waypoint.class))
            .setDestination(conversionService.convert(destination, Waypoint.class))
            .addAllIntermediates(waypoints)
            .setRoutingPreference(RoutingPreference.ROUTING_PREFERENCE_UNSPECIFIED)
            .setComputeAlternativeRoutes(false)
            .setPolylineQuality(PolylineQuality.OVERVIEW)
            .setTravelMode(RouteTravelMode.BICYCLE)
            .build();

        responseRoutes = routesClient.computeRoutes(request).getRoutesList();

      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return responseRoutes;
  }

}