package org.evelyn.library.authentication.config.controller;

import org.evelyn.library.authentication.config.IRemoteConfigRefresher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RefreshController {
  private final IRemoteConfigRefresher remoteConfigRefresher;

  public RefreshController(IRemoteConfigRefresher remoteConfigRefresher) {
    this.remoteConfigRefresher = remoteConfigRefresher;
  }

  @GetMapping("/refresh")
  public void refresh() {
    remoteConfigRefresher.refresh();
  }
}
