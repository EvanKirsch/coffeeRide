package org.kirsch.model;

import lombok.Data;

@Data
public class PathfindingRequest {

  private double orgLat;
  private double orgLng;
  private double dstLat;
  private double dstLng;
  private double step;

}