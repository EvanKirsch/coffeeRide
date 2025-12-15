package org.kirsch;

import com.google.maps.places.v1.Place;
import com.google.type.LatLng;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.kirsch.model.Node;
import org.kirsch.model.WeightedPlaceGraph;
import org.kirsch.service.api.ISearchNearbyPlacesApiWrapper;
import org.kirsch.service.api.SearchNearbyPlacesApiWrapper;
import org.kirsch.service.pathfinding.EdgeCalculator;
import org.kirsch.service.pathfinding.GraphFactory;
import org.kirsch.util.DistanceCalculator;

public class Main {
  public static void main(String[] args) throws IOException {
    ISearchNearbyPlacesApiWrapper wrapper = new SearchNearbyPlacesApiWrapper();
    LatLng origin = createLatLng(args[0], args[1]);
    LatLng destination = createLatLng(args[2], args[3]);
    LatLng target;
    List<Place> bestRoute = new ArrayList<>();
    do {
      target = DistanceCalculator.findNextTarget(origin, destination, 0.3);
      List<Place> places = wrapper.doGet(origin, target);
      WeightedPlaceGraph graph = GraphFactory.buildWeightedPlaceGraph(places, origin, target);
      EdgeCalculator.sortNodes(graph);
      origin = graph.getNodes().get(0).getPlace().getLocation();
      bestRoute.add(graph.getNodes().get(0).getPlace());
    } while (target != destination);
    printPlaces(bestRoute);
  }

  private static LatLng createLatLng(String lat, String lng) {
    return LatLng.newBuilder()
        .setLatitude(Float.parseFloat(lat))
        .setLongitude(Float.parseFloat(lng))
        .build();
  }

  private static void printPlaces(List<Place> places) {
    for (Place place : places) {
      System.out.println( place.getDisplayName().getText() + ", " + place.getFormattedAddress());
    }
  }

}