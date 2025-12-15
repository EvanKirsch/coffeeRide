package org.kirsch.model;


import java.util.List;

public class WeightedPlaceGraph {

  private List<Node> nodes;

  public WeightedPlaceGraph(List<Node> nodes) {
    this.nodes = nodes;
  }

  public List<Node> getNodes() {
    return this.nodes;
  }

}