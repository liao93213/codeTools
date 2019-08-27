package liao.code.generator.back.javacode;

import liao.parse.table.model.Table;
import liao.utils.NameUtils;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by ao on 2017/10/23.
 */
@Component
public class ManagerImplClassGenerator extends AbstractClassGenerator {
    private static final String CONFIG_FILE = "ManagerImpl";

    @Override
    protected String getConfFile() {
        return CONFIG_FILE;
    }

    @Override
    protected String getFileName(Table table) {
        return "manager"+ File.separator+"impl"+ File.separator+NameUtils.getClassName(table.getTableName())+"ManagerImpl.java";
    }
}
