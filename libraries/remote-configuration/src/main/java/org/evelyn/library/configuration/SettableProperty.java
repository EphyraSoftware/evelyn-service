package org.evelyn.library.configuration;

import java.lang.reflect.Method;

class SettableProperty {
  private Method propertySetter;
  private Object target;

  Method getPropertySetter() {
    return propertySetter;
  }

  void setPropertySetter(Method propertySetter) {
    this.propertySetter = propertySetter;
  }

  Object getTarget() {
    return target;
  }

  void setTarget(Object target) {
    this.target = target;
  }
}
