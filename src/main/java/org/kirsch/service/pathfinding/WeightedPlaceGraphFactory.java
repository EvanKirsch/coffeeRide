package org.kirsch.service.pathfinding;

import com.google.maps.places.v1.Place;
import com.google.type.LatLng;
import java.util.ArrayList;
import java.util.List;
import org.kirsch.model.Node;
import org.kirsch.model.WeightedPlaceGraph;
import org.kirsch.util.DistanceCalculator;
import org.springframework.stereotype.Service;

@Service
public class WeightedPlaceGraphFactory implements IPlaceGraphFactory {

  public WeightedPlaceGraph createGraph(List<Place> places, LatLng origin, LatLng target) {
    List<Node> nodes = new ArrayList<>();
    for (Place place : places) {
      long distanceToTarget = DistanceCalculator.approxCrowDistance(place.getLocation(), target);
      long distanceToStart = DistanceCalculator.approxCrowDistance(place.getLocation(), origin);
      nodes.add(new Node(place, new ArrayList<>(), distanceToTarget, distanceToStart));
    }
    return new WeightedPlaceGraph(nodes);
  }

}