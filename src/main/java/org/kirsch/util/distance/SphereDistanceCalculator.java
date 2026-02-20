package org.kirsch.util.distance;

import com.google.type.LatLng;
import org.springframework.stereotype.Component;

// notes on distance calculations on earth: https://www.movable-type.co.uk/scripts/gis-faq-5.1.html
// package public to force interface use outside of package
@Component
final class SphereDistanceCalculator extends DistanceCalculator {

  private static final int EARTH_RADIUS_METERS = 6371000;

  public SphereDistanceCalculator build() {
    return new SphereDistanceCalculator();
  }

  @Override
  public double approxDistance(LatLng p0, LatLng p1) {
    return this.haversineImpl(p0, p1);
  }

  double haversineImpl(LatLng p0, LatLng p1) {
    double dPhi = Math.sqrt(Math.pow(p0.getLatitude() - p1.getLatitude(), 2));
    double dLam = Math.sqrt(Math.pow(p0.getLongitude() - p1.getLongitude(), 2));

    double havPhi = Math.pow(Math.sin(Math.toRadians(dPhi/2)),2);
    double havLam = Math.pow(Math.sin(Math.toRadians(dLam/2)),2);
    double cPhi0 = Math.cos(Math.toRadians(p0.getLatitude()));
    double cPhi1 = Math.cos(Math.toRadians(p1.getLatitude()));

    double havTheta = havPhi + (cPhi0 * cPhi1 * havLam);
    double theta = 2 * Math.asin(Math.min(1, Math.sqrt(havTheta)));

    return theta * EARTH_RADIUS_METERS;
  }

  double relativeGapDist(LatLng p0, LatLng p1, double stepMeters) {
    double avgLat = (p0.getLatitude() + p1.getLatitude()) / 2;

    double latDif = Math.pow(p0.getLatitude() - p1.getLatitude(), 2);
    double lngDif = Math.pow(p0.getLongitude() - p1.getLongitude(), 2);

    double diff = (latDif + lngDif) == 0 ? Double.MIN_VALUE : (latDif + lngDif); // prevent divide by 0

    double uvLat = latDif / diff;
    double uvLng = lngDif / diff;

    double partLat = uvLat * stepMeters;
    double partLng = uvLng * stepMeters * Math.cos(Math.toRadians(avgLat));

    return Math.abs(partLat) + Math.abs(partLng);
  }

  // TODO - test
  public LatLng findNextTarget(LatLng p0, LatLng p1, double flatGap) {
    double vLat = p0.getLatitude() - p1.getLatitude();
    double vLng = p0.getLongitude() - p1.getLongitude();
    double approxLatLngDist = approxDistance(p0, p1);
    double approxGapDist = relativeGapDist(p0, p1, flatGap);

    if (approxGapDist >= approxLatLngDist) {
      return p1;
    } else {
      double uvLat = ((vLat / approxLatLngDist) * approxGapDist);
      double uvLng = ((vLng / approxLatLngDist) * approxGapDist);
      return LatLng.newBuilder()
          .setLatitude(p0.getLatitude() - uvLat)
          .setLongitude(p0.getLongitude() - uvLng)
          .build();
    }
  }

}