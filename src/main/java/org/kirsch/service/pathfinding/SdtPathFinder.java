package org.kirsch.service.pathfinding;

import com.google.maps.places.v1.Place;
import com.google.type.LatLng;
import java.util.ArrayList;
import java.util.List;
import org.kirsch.model.CoffeeRidePlace;
import org.kirsch.model.Node;
import org.kirsch.model.PathfindingRequest;
import org.kirsch.model.PathfindingResponse;
import org.kirsch.model.WeightedPlaceGraph;
import org.kirsch.service.api.ISearchNearbyPlacesApiWrapper;
import org.kirsch.service.api.SearchNearbyPlacesApiWrapper;
import org.kirsch.util.DebugUtil;
import org.kirsch.util.DistanceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SdtPathFinder implements IPathFinder {

  private final ISearchNearbyPlacesApiWrapper searchPlacesWrapper;
  private final EdgeCalculator edgeCalculator;
  private final DebugUtil debugUtil;
  private final IPlaceGraphFactory graphFactory;

  @Autowired
  public SdtPathFinder(SearchNearbyPlacesApiWrapper searchPlacesWrapper,
      EdgeCalculator edgeCalculator,
      IPlaceGraphFactory graphFactory) {
    this.searchPlacesWrapper = searchPlacesWrapper;
    this.edgeCalculator = edgeCalculator;
    this.graphFactory = graphFactory;
    this.debugUtil = DebugUtil.getInstance();
  }

  @Override
  public PathfindingResponse buildRoute(PathfindingRequest pathfindingRequest) {
    LatLng target;
    List<CoffeeRidePlace> bestRoute = new ArrayList<>();
    LatLng origin = LatLng.newBuilder()
        .setLatitude(pathfindingRequest.getOrgLat())
        .setLongitude(pathfindingRequest.getOrgLng())
        .build();
    bestRoute.add(new CoffeeRidePlace(origin));
    LatLng destination = LatLng.newBuilder()
        .setLatitude(pathfindingRequest.getDstLat())
        .setLongitude(pathfindingRequest.getDstLng())
        .build();
    double step = Math.max(pathfindingRequest.getStep(), 0.01);
    boolean isDeadEnd = false;
    do {
      target = DistanceCalculator.findNextTarget(origin, destination, step);
      List<Place> places = searchPlacesWrapper.doGet(origin, target);
      WeightedPlaceGraph graph = graphFactory.createGraph(places, origin, target);
      edgeCalculator.sortNodes(graph);
      List<Node> nodes = graph.getNodes();
      if (nodes != null && !nodes.isEmpty()) {
        origin = nodes.get(0).getPlace().getLocation();
        bestRoute.add(new CoffeeRidePlace(nodes.get(0).getPlace()));
      } else {
        // TODO - solve
        isDeadEnd = true;
      }
    } while (target != destination && !isDeadEnd);
    bestRoute.add(new CoffeeRidePlace(destination));
    return new PathfindingResponse(bestRoute);
  }

}