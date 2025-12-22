package org.kirsch.service.api;

import com.google.maps.places.v1.Place;
import com.google.maps.routing.v2.Route;
import com.google.type.LatLng;
import java.util.List;

public interface IRoutesApiWrapper {

  List<Route> computeRoute(LatLng origin, LatLng destination, List<Place> intermediates);

}
