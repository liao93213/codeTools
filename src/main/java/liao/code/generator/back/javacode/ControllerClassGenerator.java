package liao.code.generator.back.javacode;




import liao.parse.table.model.Table;
import liao.utils.NameUtils;

import java.io.File;

/**
 * Created by ao on 2017/10/23.
 */
public class ControllerClassGenerator extends AbstractClassGenerator{
    private static final String CONFIG_FILE = "Controller";

    @Override
    protected String getConfFile() {
        return CONFIG_FILE;
    }

    @Override
    protected String getFileName(Table table) {
        return "controller" + File.separator + NameUtils.getClassName(table.getTableName()) + "Controller.java";
    }
}
