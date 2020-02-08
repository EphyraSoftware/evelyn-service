package org.evelyn.services.todo.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;

@SpringBootApplication
@ComponentScan(basePackages = {"org.evelyn.services.todo", "org.evelyn.library.authentication"})
public class WebApp {
  public static void main(String[] args) {
    configureTrustStore();

    SpringApplication.run(WebApp.class, args);
  }

  private static void configureTrustStore() {
    var trustStore = "/etc/evelyn/truststore.p12";
    var trustStoreFile = new File(trustStore);
    if (trustStoreFile.isFile() && trustStoreFile.canRead()) {
      System.out.println("Configuring truststore.");
      System.setProperty("javax.net.ssl.trustStoreType", "PKCS12");
      System.setProperty("javax.net.ssl.trustStore", trustStore);
      System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
    }
  }
}
