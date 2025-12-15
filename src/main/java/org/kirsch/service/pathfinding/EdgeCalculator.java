package org.kirsch.service.pathfinding;

import org.kirsch.model.Node;
import org.kirsch.model.WeightedPlaceGraph;

public class EdgeCalculator {

  public static WeightedPlaceGraph sortNodes(WeightedPlaceGraph graph) {
    graph.getNodes().sort((n0, n1) -> {
      return n0.getDistanceToTerminus() < n1.getDistanceToTerminus() ? -1 : 1;
    });

    graph.getNodes()
        .removeIf(elt -> elt.getDistanceToStart() == 0);

    printGraph(graph);
    return graph;
  }

  private static void printGraph(WeightedPlaceGraph graph) {
    for (Node node : graph.getNodes()) {
      System.out.println(node.getDistanceToTerminus() + ", " + node.getDistanceToStart()
          + ", " + node.getPlace().getDisplayName().getText()
          + ", " + node.getPlace().getFormattedAddress());
    }
  }

}