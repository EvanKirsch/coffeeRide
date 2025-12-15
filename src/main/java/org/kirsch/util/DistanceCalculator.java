package org.kirsch.util;

import com.google.type.LatLng;

public class DistanceCalculator {

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

  public static LatLng findNextTarget(LatLng p0, LatLng p1, double gapSize) {
    double vLat = p0.getLatitude() - p1.getLatitude();
    double vLng = p0.getLongitude() - p1.getLongitude();
    double latDif = p0.getLatitude() - p1.getLatitude();
    double lngDif = p0.getLongitude() - p1.getLongitude();
    double latLngDist = Math.sqrt(Math.pow(latDif, 2) + Math.pow(lngDif, 2));

    if (gapSize >= latLngDist) {
      return p1;
    } else {
      double uvLat = ((vLat / latLngDist) * gapSize);
      double uvLng = ((vLng / latLngDist) * gapSize);
      return LatLng.newBuilder()
          .setLatitude(p0.getLatitude() - uvLat)
          .setLongitude(p0.getLongitude() - uvLng)
          .build();
    }
  }
}