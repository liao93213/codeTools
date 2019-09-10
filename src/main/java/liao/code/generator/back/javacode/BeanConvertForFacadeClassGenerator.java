package liao.code.generator.back.javacode;


import liao.parse.table.model.Table;
import liao.utils.NameUtils;

import java.io.File;

/**
 * Created by cheng on 2019/8/30.
 */
public class BeanConvertForFacadeClassGenerator extends BeanClassGenerator{
    private static final String CONFIG_FILE = "DtoConvertModel";

    protected String getConfFile() {
        return CONFIG_FILE;
    }

    public String getFileName(Table table) {
        return "convert/dto" + File.separator + NameUtils.getClassName(table.getTableName()) + "Convert.java";
    }
}
