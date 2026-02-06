package org.kirsch.service.api;

import org.springframework.stereotype.Service;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.type.LatLng;

import org.kirsch.util.ApplicationProperties;

@Service
public class GeocodeApiWrapper implements IGeocodeApiWrapper {

  private String apiKey = ApplicationProperties.getInstance().getGoogleApiKey();

  public LatLng geocode(String address) {
    LatLng latLng = null;
    GeoApiContext context = new GeoApiContext.Builder()
      .apiKey(apiKey)
      .build();

    GeocodingResult[] response;
    try {
      response =  GeocodingApi
        .geocode(context, address)
        .await();
      System.out.println(response[0]);

      latLng = LatLng.newBuilder()
        .setLatitude(response[0].geometry.location.lat)
        .setLongitude( response[0].geometry.location.lng)
        .build();

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      context.shutdown();
    }

    return latLng;
  }
    
}