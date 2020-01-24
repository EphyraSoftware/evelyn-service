package org.evelyn.library.authentication.token;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class TokenLookup {
  @SuppressWarnings("unchecked")
  public TokenInfo getTokenInfo() {
    KeycloakAuthenticationToken authentication =
            (KeycloakAuthenticationToken) SecurityContextHolder
                    .getContext()
                    .getAuthentication();

    KeycloakPrincipal<KeycloakSecurityContext> keycloakPrincipal =
            (KeycloakPrincipal<KeycloakSecurityContext>) authentication
                    .getPrincipal();

    return new TokenInfo(keycloakPrincipal.getKeycloakSecurityContext().getToken());
  }
}
