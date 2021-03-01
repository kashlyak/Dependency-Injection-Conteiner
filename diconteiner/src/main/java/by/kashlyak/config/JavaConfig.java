package by.kashlyak.config;



import by.kashlyak.interfaces.Injector;

import java.util.Map;

public class JavaConfig implements Configuration{
    @Override
    public String getPackageToScan() {
        return "by";
    }

    @Override
    public Map<Class, Class> getInterfaceToImplementation() {
        return Map.of(Injector.class, Injector.class);
    }


}
