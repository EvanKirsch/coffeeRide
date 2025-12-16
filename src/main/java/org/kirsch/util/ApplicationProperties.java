package org.kirsch.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApplicationProperties extends Properties {

  private static ApplicationProperties applicationProperties;

  private ApplicationProperties() {
    try {
      this.load(new FileInputStream("src/main/resources/application.properties"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static ApplicationProperties getInstance() {
    if(applicationProperties == null) {
      applicationProperties = new ApplicationProperties();
    }
    return applicationProperties;
  }

  public String getGoogleApiKey() {
    return applicationProperties.getProperty("com.google.api.key");
  }

  public Integer getPlacesMaxResultCount() {
    int maxResultCount = 0;
    try {
      String str = applicationProperties.getProperty("com.google.maps.places.max_result_count", "10");
      maxResultCount = Integer.parseInt(str);
    } catch (NumberFormatException e) {
      // eat error
      maxResultCount = 10;
    }
    return maxResultCount;
  }

}