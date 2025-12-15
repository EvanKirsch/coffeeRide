package org.kirsch.service.api;

import com.google.type.LatLng;

public interface IRoutesApiWrapper {

  void doGet(LatLng origin, LatLng destination);

}
