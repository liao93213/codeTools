package liao.code.generator.back.javacode;


import liao.parse.table.model.Table;
import liao.utils.NameUtils;

import java.io.File;

/**
 * Created by cheng on 2019/8/27.
 */
public class DomianModelClassGenerator extends BeanClassGenerator {
    private static final String CONFIG_FILE = "DomainModel";
    public String getFileName(Table table){
        return "model" + File.separator + NameUtils.getClassName(table.getTableName()) + ".java";
    }

    @Override
    protected String getConfFile() {
        return CONFIG_FILE;
    }

}
