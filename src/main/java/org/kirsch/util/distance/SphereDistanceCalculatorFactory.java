package org.kirsch.util.distance;


import org.springframework.stereotype.Service;

@Service
public final class SphereDistanceCalculatorFactory extends DistanceCalculatorFactory {

  DistanceCalculator createCalculator() {
    return new SphereDistanceCalculator();
  }

}