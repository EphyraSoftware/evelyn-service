package org.evelyn.library.authentication;

import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class KeycloakRestTemplateConfiguration {
  public final KeycloakClientRequestFactory keycloakClientRequestFactory;

  public KeycloakRestTemplateConfiguration(KeycloakClientRequestFactory keycloakClientRequestFactory) {
    this.keycloakClientRequestFactory = keycloakClientRequestFactory;
  }

  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public KeycloakRestTemplate keycloakRestTemplate() {
    return new KeycloakRestTemplate(keycloakClientRequestFactory);
  }
}