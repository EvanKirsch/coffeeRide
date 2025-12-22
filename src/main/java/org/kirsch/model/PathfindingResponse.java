package org.kirsch.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PathfindingResponse {

  private List<CoffeeRideLeg> legs;
  private String encodedPolyline;

}