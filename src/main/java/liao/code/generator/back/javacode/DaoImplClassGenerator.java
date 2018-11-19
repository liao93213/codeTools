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
public class DaoImplClassGenerator  extends AbstractClassGenerator {
    private static final String CONFIG_FILE = "DaoImpl";

    @Override
    protected String getConfFile() {
        return CONFIG_FILE;
    }

    @Override
    protected String getFileName(Table table) {
        return "dao"+ File.separator+"impl"+ File.separator+NameUtils.getClassName(table.getTableName())+"DaoImpl.java";
    }

    public static class DaoImplFactory implements Factory<DaoImplClassGenerator> {
        @Override
        public DaoImplClassGenerator create() {
            return new DaoImplClassGenerator();
        }
    }
}
