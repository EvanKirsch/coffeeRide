package org.kirsch.service.pathfinding;

import com.google.maps.places.v1.Place;
import com.google.type.LatLng;
import java.util.ArrayList;
import java.util.List;
import org.kirsch.model.WeightedPlaceGraph;
import org.kirsch.service.api.ISearchNearbyPlacesApiWrapper;
import org.kirsch.service.api.SearchNearbyPlacesApiWrapper;
import org.kirsch.util.DebugUtil;
import org.kirsch.util.DistanceCalculator;

public class SdtPathFinder implements IPathFinder {

  private final ISearchNearbyPlacesApiWrapper searchPlacesWrapper;
  private final EdgeCalculator edgeCalculator;
  private final DebugUtil debugUtil;
  private final PlaceGraphFactory graphFactory;

  public SdtPathFinder() {
    this.searchPlacesWrapper = new SearchNearbyPlacesApiWrapper();
    this.edgeCalculator = new EdgeCalculator();
    this.debugUtil = DebugUtil.getInstance();
    this.graphFactory = new WeightedPlaceGraphFactory();
  }

  @Override
  public List<Place> buildRoute(LatLng origin, LatLng destination) {
    LatLng target;
    List<Place> bestRoute = new ArrayList<>();
    do {
      target = DistanceCalculator.findNextTarget(origin, destination, 0.3);
      List<Place> places = searchPlacesWrapper.doGet(origin, target);
      WeightedPlaceGraph graph = graphFactory.createGraph(places, origin, target);
      edgeCalculator.sortNodes(graph);
      origin = graph.getNodes().get(0).getPlace().getLocation();
      bestRoute.add(graph.getNodes().get(0).getPlace());
    } while (target != destination);
    debugUtil.printPlaces(bestRoute);
    return bestRoute;
  }

}