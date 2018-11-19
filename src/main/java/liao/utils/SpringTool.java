package liao.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SpringTool implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    public ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    public <T> List<T> getBeanList(Class<T> clazz){
        Map<String, T> beanMap = applicationContext.getBeansOfType(clazz);
        return new ArrayList<>(beanMap.values());

    }

    public <T> T getBeanByName(String name){
         return (T) applicationContext.getBean(name);

    }
}
