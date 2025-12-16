package org.kirsch.controller;

import com.google.type.LatLng;

public interface IPathfindingController {

  void getRoute(LatLng origin, LatLng destination);

}