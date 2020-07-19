package org.evelyn.library.authentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Value("${org.evelyn.tlsEnabled}")
  private boolean tlsEnabled;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    var httpChain = http
            .csrf().disable() // TODO temp!
            .authorizeRequests(authorize -> authorize.antMatchers(HttpMethod.OPTIONS, "**").permitAll()
                    .anyRequest().authenticated())
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

    if (tlsEnabled) {
      httpChain.requiresChannel().anyRequest().requiresSecure();
    }
  }
}
