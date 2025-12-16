package org.kirsch.controller;

import com.google.type.LatLng;
import org.kirsch.service.pathfinding.IPathFinder;
import org.kirsch.service.pathfinding.SdtPathFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pathfinding")
public class PathfindingController implements IPathfindingController {

  private final IPathFinder pathFinder;

  @Autowired
  public PathfindingController(SdtPathFinder pathFinder) {
    this.pathFinder = pathFinder;
  }

  @GetMapping
  @Override
  public void getRoute(LatLng origin, LatLng destination) {
    pathFinder.buildRoute(origin, destination);
  }

}