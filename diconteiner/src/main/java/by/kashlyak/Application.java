package by.kashlyak;

import by.kashlyak.context.ApplicationContext;
import by.kashlyak.factory.BeanFactory;

public class Application {
    public ApplicationContext run() {
        ApplicationContext applicationContext = new ApplicationContext();
        BeanFactory beanFactory = new BeanFactory(applicationContext);
        applicationContext.setBeanFactory(beanFactory);
        return applicationContext;
    }

    public static void main(String[] args) {
        Application application = new Application();
        ApplicationContext context = application.run();
    }
}
