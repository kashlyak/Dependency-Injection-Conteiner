package by.kashlyak.factory;



import by.kashlyak.exceptions.ConstructorNotFoundException;
import by.kashlyak.exceptions.TooManyConstructorsException;
import by.kashlyak.config.Configuration;
import by.kashlyak.config.JavaConfig;
import by.kashlyak.configurator.BeanConfigurator;
import by.kashlyak.configurator.JavaBeanConfigurator;
import by.kashlyak.context.ApplicationContext;
import by.kashlyak.annotation.Inject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.*;

public class BeanFactory {

    private final BeanConfigurator beanConfigurator;
    private final Configuration configuration;
    private ApplicationContext applicationContext;

    public BeanFactory(ApplicationContext applicationContext) {
        this.configuration = new JavaConfig();
        this.beanConfigurator = new JavaBeanConfigurator(configuration.getPackageToScan(), configuration.getInterfaceToImplementation());
        this.applicationContext = applicationContext;
    }

    public <T> T getBean(Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<? extends T> implementationClass = clazz;
        if (implementationClass.isInterface()) {
            implementationClass = beanConfigurator.getImplementationClass(implementationClass);
        }
        T bean;
      try {
           bean = implementationClass.getDeclaredConstructor().newInstance();
      }catch (NoSuchMethodException ex) {
          throw new ConstructorNotFoundException("There is no default constructor");

      }

        Constructor<?>[] declaredConstructors = implementationClass.getDeclaredConstructors();
        ArrayList<Object> beans = new ArrayList<>();
        int count = 0;

        for (Constructor<?> constr : declaredConstructors) {

            if (constr.isAnnotationPresent(Inject.class)) {
                count++;
            }

        }
        if (count > 1) {
            throw new TooManyConstructorsException("Too many constructors with annotation @Inject");
        }

        for (Constructor<?> constr : declaredConstructors) {
            if (constr.isAnnotationPresent(Inject.class)) {

                Class<?>[] parameterTypes = constr.getParameterTypes();

                for (Class<?> param : parameterTypes) {
                    Object bean1 = applicationContext.getBean(param);
                    beans.add(bean1);
                }
                return (T) constr.newInstance(beans.toArray());
            }
        }
        return bean;
    }

}
