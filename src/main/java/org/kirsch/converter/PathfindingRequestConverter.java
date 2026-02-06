package org.kirsch.converter;

import lombok.NonNull;
import org.kirsch.model.PathfindingRequest;
import org.kirsch.model.PathfindingRequestStr;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public final class PathfindingRequestConverter implements Converter<PathfindingRequestStr, PathfindingRequest> {

  @Override
  public PathfindingRequest convert(@NonNull PathfindingRequestStr source) {
    double step = convertStringToDouble(source.getStep());

    return PathfindingRequest.builder()
        .orgAddress(source.getOrigin())
        .dstAddress(source.getDestination())
        .step(step)
        .build();
  }

  private Double convertStringToDouble(String s) {
    if (s.isEmpty()) {
      return 0.0;
    }

    double d;
    try {
      d = Double.parseDouble(s);
    } catch (NumberFormatException e) {
      e.printStackTrace();
      d = 0;
    }
    return d;
  }

}