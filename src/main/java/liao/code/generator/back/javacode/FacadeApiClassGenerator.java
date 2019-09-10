package liao.code.generator.back.javacode;


import liao.parse.table.model.Table;
import liao.utils.NameUtils;

import java.io.File;

/**
 * Created by cheng on 2019/9/2.
 */
public class FacadeApiClassGenerator extends AbstractClassGenerator{
    private static final String CONFIG_FILE = "FacadeApiModel";

    @Override
    protected String getConfFile() {
        return CONFIG_FILE;
    }

    @Override
    protected String getFileName(Table table) {
        return "facade"+ File.separator+ NameUtils.getClassName(table.getTableName())+"Facade.java";
    }
}
