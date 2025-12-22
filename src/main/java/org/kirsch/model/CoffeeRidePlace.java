package org.kirsch.model;

import com.google.maps.places.v1.Place;
import com.google.type.LatLng;
import lombok.Data;

@Data
public class CoffeeRidePlace {

  private final String displayName;
  private final String address;
  private final double lat;
  private final double lng;
  private final String name;

  public CoffeeRidePlace(Place place) {
    this.displayName = place.getDisplayName().getText();
    this.address = place.getFormattedAddress();
    this.lat = place.getLocation().getLatitude();
    this.lng = place.getLocation().getLongitude();
    this.name = place.getName();
  }

  public CoffeeRidePlace(LatLng latLng) {
    this.displayName = "";
    this.address = "";
    this.lat = latLng.getLatitude();
    this.lng = latLng.getLongitude();
    this.name = "";
  }

}