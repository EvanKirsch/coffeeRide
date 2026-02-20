package org.kirsch.util.distance;

import com.google.type.LatLng;

abstract class DistanceCalculator implements IDistanceCalculator {


  abstract DistanceCalculator build();
  public abstract double approxDistance(LatLng p0, LatLng p1);
  abstract double relativeGapDist(LatLng p0, LatLng p1, double flatGap);
  public abstract LatLng findNextTarget(LatLng p0, LatLng p1, double flatGap);

  public LatLng getCenter(LatLng p0, LatLng p1) {
    double latMid = (p0.getLatitude() + p1.getLatitude()) / 2;
    double lngMid = (p0.getLongitude() + p1.getLongitude()) / 2;

    return LatLng.newBuilder()
        .setLatitude(latMid)
        .setLongitude(lngMid)
        .build();
  }

}