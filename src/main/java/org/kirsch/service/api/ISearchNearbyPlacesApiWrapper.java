package org.kirsch.service.api;

import com.google.maps.places.v1.Place;
import com.google.type.LatLng;
import java.util.List;

public interface ISearchNearbyPlacesApiWrapper {

  List<Place> searchNearby(LatLng origin, LatLng destination);

}
