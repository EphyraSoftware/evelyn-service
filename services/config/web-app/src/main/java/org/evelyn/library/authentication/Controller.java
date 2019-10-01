package org.evelyn.library.authentication;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.evelyn.library.authentication.config.EnableEtcdConfigServer;
import org.evelyn.library.authentication.config.IRemoteConfigRefresher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@SpringBootApplication
@CrossOrigin("*")
@EnableEtcdConfigServer
public class Controller {

  @Autowired
  public ServiceConfig serviceConfig;

  @Autowired
  private IRemoteConfigRefresher remoteConfigRefresher;

  public static void main(String[] args) {
    SpringApplication.run(Controller.class, args);
  }

  //@PreAuthorize("isAuthenticated()")
  @GetMapping("/test")
  @ResponseBody
  public TestModel root() {
    TestModel testModel = new TestModel();
    testModel.value = serviceConfig.getValue();
    return testModel;
  }

  @PostConstruct
  public void post() {
    remoteConfigRefresher.refresh();
  }
}

@JsonSerialize
class TestModel {
  public String value;
}
