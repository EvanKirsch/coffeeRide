package org.kirsch.util;

import com.google.type.LatLng;

public final class DistanceCalculator {

  public static LatLng approxCenter(LatLng p0, LatLng p1) {
    double latMid = (p0.getLatitude() + p1.getLatitude()) / 2;
    double lngMid = (p0.getLongitude() + p1.getLongitude()) / 2;

    return LatLng.newBuilder()
        .setLatitude(latMid)
        .setLongitude(lngMid)
        .build();
  }

  public static long approxCrowDistance(LatLng p0, LatLng p1) {
    double latDif = p0.getLatitude() - p1.getLatitude();
    double lngDif = p0.getLongitude() - p1.getLongitude();
    double latLngDist = Math.sqrt(Math.pow(latDif, 2) + Math.pow(lngDif, 2));

    return Math.round(latLngDist * 111111.1);
  }

  public static LatLng findNextTarget(LatLng p0, LatLng p1, double flatGap) {
    double vLat = p0.getLatitude() - p1.getLatitude();
    double vLng = p0.getLongitude() - p1.getLongitude();
    double latDif = p0.getLatitude() - p1.getLatitude();
    double lngDif = p0.getLongitude() - p1.getLongitude();
    double latLngDist = Math.sqrt(Math.pow(latDif, 2) + Math.pow(lngDif, 2));
    double approxDegreeGap = approxGapSizeFromMilesToDegrees(p0, p1, flatGap);

    if (approxDegreeGap >= latLngDist) {
      return p1;
    } else {
      double uvLat = ((vLat / latLngDist) * approxDegreeGap);
      double uvLng = ((vLng / latLngDist) * approxDegreeGap);
      return LatLng.newBuilder()
          .setLatitude(p0.getLatitude() - uvLat)
          .setLongitude(p0.getLongitude() - uvLng)
          .build();
    }
  }

  // TODO - I think there is an issue w/ negative directions. Need to work on tests
  private static double approxGapSizeFromMilesToDegrees(LatLng p0, LatLng p1, double flatGap) {
    double avgLat = (p0.getLatitude() + p1.getLatitude()) / 2;

    double cnvFactLat = 69.1;
    double cnvFactLng = 69.1 * Math.cos(avgLat);

    double latDif = Math.pow(p0.getLatitude() - p1.getLatitude(), 2);
    double lngDif = Math.pow(p0.getLongitude() - p1.getLongitude(), 2);

    double uvLat = latDif / (latDif + lngDif);
    double uvLng = lngDif / (latDif + lngDif);

    double partLat = uvLat * (flatGap / cnvFactLat);
    double partLng = uvLng * (flatGap / cnvFactLng);

    return Math.abs(partLat) + Math.abs(partLng);
  }

}