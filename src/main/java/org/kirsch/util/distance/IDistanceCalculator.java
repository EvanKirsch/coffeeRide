package org.kirsch.util.distance;

import com.google.type.LatLng;

public interface IDistanceCalculator {

  double approxDistance(LatLng p0, LatLng p1);
  LatLng getCenter(LatLng p0, LatLng p1);
  LatLng findNextTarget(LatLng p0, LatLng p1, double stepMeters);

}