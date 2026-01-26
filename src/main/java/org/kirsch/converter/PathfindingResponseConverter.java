package org.kirsch.converter;

import com.google.maps.routing.v2.Route;
import com.google.maps.routing.v2.RouteLeg;
import java.util.ArrayList;
import java.util.List;
import org.kirsch.model.CoffeeRideLeg;
import org.kirsch.model.CoffeeRidePlace;
import org.kirsch.model.PathfindingResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public final class PathfindingResponseConverter implements Converter<Route, PathfindingResponse> {

  @Override
  public PathfindingResponse convert(Route route) {
    String encodedPolyline = route.getPolyline().getEncodedPolyline();
    List<CoffeeRideLeg> legs = new ArrayList<>();

    for(RouteLeg leg : route.getLegsList()) {
      legs.add(convertLeg(leg));
    }

    return new PathfindingResponse(legs, encodedPolyline);
  }

  private CoffeeRideLeg convertLeg(RouteLeg leg) {
    CoffeeRidePlace origin = new CoffeeRidePlace(leg.getStartLocation().getLatLng());
    CoffeeRidePlace destination = new CoffeeRidePlace(leg.getEndLocation().getLatLng());

    return new CoffeeRideLeg(origin, destination, leg.getPolyline().getEncodedPolyline());
  }

}