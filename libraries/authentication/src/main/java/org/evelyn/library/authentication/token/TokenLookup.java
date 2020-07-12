package org.evelyn.library.authentication.token;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class TokenLookup {
  public TokenInfo getTokenInfo(Jwt token) {
    if (!token.getClaims().containsKey("email")) {
      throw new SecurityException("Will not accept a token without an email claim");
    }

    return new TokenInfo(token.getSubject(), token.getClaimAsString("email"));
  }
}
