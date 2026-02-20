package org.kirsch.model;

import com.google.maps.places.v1.Place;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Node {

  private final Place place;
  private final double distanceToTerminus;
  private final double distanceToStart;

}