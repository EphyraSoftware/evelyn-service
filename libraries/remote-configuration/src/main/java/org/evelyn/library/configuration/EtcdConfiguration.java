package org.evelyn.library.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
@ComponentScan("org.evelyn.library.configuration")
class EtcdConfiguration {
  private static final Set<Class> primitiveTypes = new HashSet<>(Arrays.asList(
          Boolean.class,
          Character.class,
          Byte.class,
          Short.class,
          Integer.class,
          Long.class,
          Float.class,
          Double.class,
          String.class
  ));

  @Bean
  public EtcdPropertySetter makePropertySetter(ApplicationContext applicationContext) {
    Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(ConfigurationProperties.class);

    var properties = new HashMap<String, SettableProperty>();

    beansWithAnnotation.forEach((name, bean) -> {
      var propertySourceAnnotation = bean.getClass().getAnnotation(ConfigurationProperties.class);
      if (propertySourceAnnotation == null) {
        return;
      }

      String prefix = propertySourceAnnotation.prefix();
      try {
        mapProperties(bean, properties, prefix);
      } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
        // Some of the property accessors from third parties do throw exceptions! I'm fairly confident we don't care
        // and users of this code can figure out what is wrong with their pojo if properties are missing.
      }
    });

    return new EtcdPropertySetter(properties);
  }

  private void mapProperties(Object bean, Map<String, SettableProperty> properties, String prefix) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
    if (bean == null) {
      return;
    }

    BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
    for (var pd : beanInfo.getPropertyDescriptors()) {
      if (pd.getPropertyType() == Class.class) {
        continue;
      }

      String propertyName = StringUtils.isEmpty(prefix) ? pd.getName() : prefix + '.' + pd.getName();

      if (primitiveTypes.contains(pd.getPropertyType())) {
        SettableProperty settableProperty = new SettableProperty();
        settableProperty.setPropertySetter(pd.getWriteMethod());
        settableProperty.setTarget(bean);
        properties.put(propertyName, settableProperty);
      }
      else {
        if (bean.getClass().equals(pd.getReadMethod().getReturnType())) {
          continue;
        }
        mapProperties(pd.getReadMethod().invoke(bean), properties, propertyName);
      }
    }
  }
}

