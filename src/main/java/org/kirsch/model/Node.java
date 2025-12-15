package org.kirsch.model;

import com.google.maps.places.v1.Place;
import java.util.List;

public class Node {

  private Place place;
  private List<Edge> edges;
  private long distanceToTerminus;
  private long distanceToStart;

  public Node(Place place, List<Edge> edges, long distanceToTerminus, long distanceToStart) {
    this.place = place;
    this.edges = edges;
    this.distanceToTerminus = distanceToTerminus;
    this.distanceToStart = distanceToStart;
  }

  public long getDistanceToTerminus() {
    return this.distanceToTerminus;
  }

  public long getDistanceToStart() {
    return this.distanceToStart;
  }

  public Place getPlace() {
    return this.place;
  }

}