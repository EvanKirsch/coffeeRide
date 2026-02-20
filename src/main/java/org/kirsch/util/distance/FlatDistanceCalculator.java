package org.kirsch.util.distance;

import com.google.type.LatLng;
import org.springframework.stereotype.Component;

@Component
final class FlatDistanceCalculator extends DistanceCalculator {

  static final double METERS_PER_DEGREE = 111111.1;

  public FlatDistanceCalculator build() {
    return new FlatDistanceCalculator();
  }

  public double approxDistance(LatLng p0, LatLng p1) {
    double latDif = p0.getLatitude() - p1.getLatitude();
    double lngDif = p0.getLongitude() - p1.getLongitude();
    double latLngDist = Math.sqrt(Math.pow(latDif, 2) + Math.pow(lngDif, 2));

    return (latLngDist * METERS_PER_DEGREE);
  }

  double relativeGapDist(LatLng p0, LatLng p1, double stepMeters) {
    return stepMeters;
  }

  public LatLng findNextTarget(LatLng p0, LatLng p1, double flatGap) {
    double vLat = p0.getLatitude() - p1.getLatitude();
    double vLng = p0.getLongitude() - p1.getLongitude();
    double latLngDist = approxDistance(p0, p1);
    double gapDist = relativeGapDist(p0, p1, flatGap);

    if (gapDist >= latLngDist) {
      return p1;
    } else {
      double uvLat = ((vLat / latLngDist) * gapDist);
      double uvLng = ((vLng / latLngDist) * gapDist);
      return LatLng.newBuilder()
          .setLatitude(p0.getLatitude() - uvLat)
          .setLongitude(p0.getLongitude() - uvLng)
          .build();
    }
  }

}