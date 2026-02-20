package org.kirsch.service.pathfinding;

import com.google.maps.places.v1.Place;
import com.google.type.LatLng;
import java.util.ArrayList;
import java.util.List;
import org.kirsch.model.Node;
import org.kirsch.model.WeightedPlaceGraph;
import org.kirsch.util.distance.IDistanceCalculator;
import org.kirsch.util.distance.SphereDistanceCalculatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeightedPlaceGraphFactory implements IPlaceGraphFactory {

  private final IDistanceCalculator distanceCalculator;

  @Autowired
  public WeightedPlaceGraphFactory(SphereDistanceCalculatorFactory dcFactory) {
    this.distanceCalculator = dcFactory.getCalculator();
  }

  public WeightedPlaceGraph createGraph(List<Place> places, LatLng origin, LatLng target) {
    List<Node> nodes = new ArrayList<>();
    for (Place place : places) {
      double distanceToTarget = distanceCalculator.approxDistance(place.getLocation(), target);
      double distanceToStart = distanceCalculator.approxDistance(place.getLocation(), origin);
      nodes.add(new Node(place, distanceToTarget, distanceToStart));
    }
    return new WeightedPlaceGraph(nodes);
  }

}