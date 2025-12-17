package org.kirsch.controller;

import org.kirsch.model.PathfindingRequest;
import org.kirsch.model.PathfindingResponse;
import org.kirsch.service.pathfinding.IPathFinder;
import org.kirsch.service.pathfinding.SdtPathFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pathfinding")
public class PathfindingController implements IPathfindingController {

  private final IPathFinder pathFinder;

  @Autowired
  public PathfindingController(SdtPathFinder pathFinder) {
    this.pathFinder = pathFinder;
  }

  @PutMapping
  @ResponseBody
  @Override
  public PathfindingResponse findRoute(@RequestBody PathfindingRequest pathfindingRequest) {
    return pathFinder.buildRoute(pathfindingRequest);
  }

}