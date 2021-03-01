package by.kashlyak.context;

import by.kashlyak.factory.BeanFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {

    private BeanFactory beanFactory;

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    private final Map<Class, Object> beanMap = new ConcurrentHashMap<>();

    public  Map<Class, Object> getBeanMap() {
        return beanMap;
    }

    public ApplicationContext() {

    }

    public <T> T getBean(Class<T> clazz) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if(beanMap.containsKey(clazz)) {
            return (T) beanMap.get(clazz);
        }

        T bean = beanFactory.getBean(clazz);

        beanMap.put(clazz, bean);
        return bean;
    }


}
