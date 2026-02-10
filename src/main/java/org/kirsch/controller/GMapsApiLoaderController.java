package org.kirsch.controller;

import org.kirsch.model.ApiKeyResponse;
import org.kirsch.util.ApplicationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gMapsApiLoader")
public class GMapsApiLoaderController implements IGMapsApiLoaderController {

  @GetMapping
  @ResponseBody
  @Override
  public ApiKeyResponse getGMapsJsApiKey() {
    return new ApiKeyResponse(ApplicationProperties.getInstance().getGoogleJsApiKey());
  }

}