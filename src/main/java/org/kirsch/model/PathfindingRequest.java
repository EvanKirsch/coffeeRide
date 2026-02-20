package org.kirsch.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PathfindingRequest {

  private final String orgAddress;
  private final String dstAddress;
  private final double stepMeters;

}