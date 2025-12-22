package org.kirsch.service.pathfinding;

import com.google.maps.places.v1.Place;
import com.google.maps.routing.v2.Route;
import com.google.type.LatLng;
import java.util.ArrayList;
import java.util.List;
import org.kirsch.model.Node;
import org.kirsch.model.PathfindingRequest;
import org.kirsch.model.WeightedPlaceGraph;
import org.kirsch.service.api.IRoutesApiWrapper;
import org.kirsch.service.api.ISearchNearbyPlacesApiWrapper;
import org.kirsch.service.api.RoutesApiWrapper;
import org.kirsch.service.api.SearchNearbyPlacesApiWrapper;
import org.kirsch.util.DistanceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SdtPathFinder implements IPathFinder {

  private final ISearchNearbyPlacesApiWrapper searchPlacesWrapper;
  private final IRoutesApiWrapper routesApiWrapper;
  private final EdgeCalculator edgeCalculator;
  private final IPlaceGraphFactory graphFactory;

  @Autowired
  public SdtPathFinder(SearchNearbyPlacesApiWrapper searchPlacesWrapper,
      RoutesApiWrapper routesApiWrapper,
      EdgeCalculator edgeCalculator,
      IPlaceGraphFactory graphFactory) {
    this.routesApiWrapper = routesApiWrapper;
    this.searchPlacesWrapper = searchPlacesWrapper;
    this.edgeCalculator = edgeCalculator;
    this.graphFactory = graphFactory;
  }

  @Override
  public List<Route> buildRoute(PathfindingRequest pathfindingRequest) {
    LatLng target;
    List<Place> bestRoute = new ArrayList<>();
    LatLng origin = LatLng.newBuilder()
        .setLatitude(pathfindingRequest.getOrgLat())
        .setLongitude(pathfindingRequest.getOrgLng())
        .build();
    LatLng curOrigin = origin;
    LatLng destination = LatLng.newBuilder()
        .setLatitude(pathfindingRequest.getDstLat())
        .setLongitude(pathfindingRequest.getDstLng())
        .build();
    double step = Math.max(pathfindingRequest.getStep(), 0.01);
    boolean isDeadEnd = false;
    do {
      target = DistanceCalculator.findNextTarget(curOrigin, destination, step);
      List<Place> places = searchPlacesWrapper.searchNearby(curOrigin, target);
      WeightedPlaceGraph graph = graphFactory.createGraph(places, curOrigin, target);
      edgeCalculator.sortNodes(graph);
      List<Node> nodes = graph.getNodes();
      if (nodes != null && !nodes.isEmpty()) {
        curOrigin = nodes.get(0).getPlace().getLocation();
        bestRoute.add(nodes.get(0).getPlace());
      } else {
        // TODO - solve
        isDeadEnd = true;
      }
    } while (target != destination && !isDeadEnd);
    List<Route> routes = routesApiWrapper.computeRoute(origin, destination, bestRoute);
    return routes;
  }

}