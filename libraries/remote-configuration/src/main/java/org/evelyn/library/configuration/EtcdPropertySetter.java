package org.evelyn.library.configuration;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

class EtcdPropertySetter {
  private Map<String, SettableProperty> properties;

  EtcdPropertySetter(Map<String, SettableProperty> properties) {
    this.properties = properties;
  }

  boolean setProperty(String name, Object value) {
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

  Set<String> getPropertyNames() {
    return properties.keySet();
  }
}
