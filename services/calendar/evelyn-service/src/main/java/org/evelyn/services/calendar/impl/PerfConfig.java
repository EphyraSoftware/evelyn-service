package org.evelyn.services.calendar.impl;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.cache.Caching;

@Configuration
public class PerfConfig {
  @PostConstruct
  public void eagerInitCache() {
    // Initialising the cache for ical4j makes the first calendar import slow, eagerly load the cache manager
    // to avoid a slow REST request.
    Caching.getCachingProvider().getCacheManager();
  }
}
