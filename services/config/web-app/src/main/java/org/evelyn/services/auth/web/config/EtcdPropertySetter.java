package org.evelyn.services.auth.web.config;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

public class EtcdPropertySetter {
  private Map<String, SettableProperty> properties;

  public EtcdPropertySetter(Map<String, SettableProperty> properties) {
    this.properties = properties;
  }

  public boolean setProperty(String name, Object value) {
    SettableProperty settableProperty = this.properties.get(name);
    if (settableProperty == null) {
      return false;
    }

    try {
      settableProperty.getPropertySetter().invoke(settableProperty.getTarget(), value);
    } catch (IllegalAccessException | InvocationTargetException e) {
      return false;
    }

    return true;
  }

  public Set<String> getPropertyNames() {
    return properties.keySet();
  }
}
