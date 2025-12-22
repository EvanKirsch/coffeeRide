package org.kirsch.service.pathfinding;

import com.google.maps.routing.v2.Route;
import java.util.List;
import org.kirsch.model.PathfindingRequest;

public interface IPathFinder {

  List<Route> buildRoute(PathfindingRequest pathfindingRequest);

}