package org.kirsch.service.pathfinding;

import com.google.maps.places.v1.Place;
import com.google.type.LatLng;
import java.util.List;
import org.kirsch.model.WeightedPlaceGraph;

public interface IPlaceGraphFactory {

  WeightedPlaceGraph createGraph(List<Place> places, LatLng origin, LatLng target);

}