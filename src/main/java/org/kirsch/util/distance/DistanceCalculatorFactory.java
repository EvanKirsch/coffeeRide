package org.kirsch.util.distance;

abstract class DistanceCalculatorFactory {

  public IDistanceCalculator getCalculator() {
    DistanceCalculator calculator = createCalculator();
    return calculator.build();
  }

  abstract DistanceCalculator createCalculator();

}