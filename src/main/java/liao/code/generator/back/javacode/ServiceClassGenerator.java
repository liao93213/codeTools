package liao.code.generator.back.javacode;

import liao.code.generator.back.factory.Factory;
import liao.parse.table.model.Table;
import liao.utils.NameUtils;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by ao on 2017/10/23.
 */
@Component
public class ServiceClassGenerator  extends AbstractClassGenerator{
    private static final String CONFIG_FILE = "IService";

    @Override
    protected String getConfFile() {
        return CONFIG_FILE;
    }

    @Override
    protected String getFileName(Table table) {
        return "service"+ File.separator+ NameUtils.getClassName(table.getTableName())+"Service.java";
    }

    public static class ServiceFactory implements Factory<ServiceClassGenerator> {

        @Override
        public ServiceClassGenerator create() {
            return new ServiceClassGenerator();
        }
    }
}
