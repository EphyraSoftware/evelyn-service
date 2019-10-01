package org.evelyn.services.auth.web.config;

import java.lang.reflect.Method;

class SettableProperty {
  private Method propertySetter;
  private Object target;

  public Method getPropertySetter() {
    return propertySetter;
  }

  public void setPropertySetter(Method propertySetter) {
    this.propertySetter = propertySetter;
  }

  public Object getTarget() {
    return target;
  }

  public void setTarget(Object target) {
    this.target = target;
  }
}
