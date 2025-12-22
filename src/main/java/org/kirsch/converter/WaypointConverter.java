package org.kirsch.converter;

import com.google.maps.routing.v2.Location;
import com.google.maps.routing.v2.Waypoint;
import com.google.type.LatLng;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public final class WaypointConverter implements Converter<LatLng, Waypoint> {

  @Override
  public Waypoint convert(@NonNull LatLng latLng) {
    return Waypoint.newBuilder()
        .setLocation(Location.newBuilder()
            .setLatLng(latLng)
            .build())
        .build();
  }

}
