package org.kirsch.controller;

import com.google.maps.routing.v2.Route;
import java.util.List;
import org.kirsch.model.PathfindingRequest;
import org.kirsch.model.PathfindingRequestStr;
import org.kirsch.model.PathfindingResponse;
import org.kirsch.service.pathfinding.IPathFinder;
import org.kirsch.service.pathfinding.SdtPathFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pathfinding")
public class PathfindingController implements IPathfindingController {

  private final IPathFinder pathFinder;
  private final ConversionService conversionService;

  @Autowired
  public PathfindingController(SdtPathFinder pathFinder, ConversionService conversionService) {
    this.pathFinder = pathFinder;
    this.conversionService = conversionService;
  }

  @PutMapping
  @ResponseBody
  @Override
  public PathfindingResponse findRoute(@RequestBody PathfindingRequestStr requestStr) {
    PathfindingRequest request = conversionService.convert(requestStr, PathfindingRequest.class);
    List<Route> route = pathFinder.buildRoute(request);
    PathfindingResponse response = conversionService.convert(route.get(0), PathfindingResponse.class);
    return response;
  }

}