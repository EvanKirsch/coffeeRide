package org.kirsch.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PathfindingRequest {

  private String orgAddress;
  private String dstAddress;
  private double step;

}