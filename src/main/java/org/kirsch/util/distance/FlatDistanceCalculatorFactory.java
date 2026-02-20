package org.kirsch.util.distance;

import org.springframework.stereotype.Service;

@Service
public final class FlatDistanceCalculatorFactory extends DistanceCalculatorFactory {

  DistanceCalculator createCalculator() {
    return new FlatDistanceCalculator();
  }

}