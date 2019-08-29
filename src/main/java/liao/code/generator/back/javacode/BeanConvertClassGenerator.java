package liao.code.generator.back.javacode;

import liao.parse.table.model.Table;
import liao.utils.NameUtils;

import java.io.File;

/**
 * Created by cheng on 2019/8/29.
 */
public class BeanConvertClassGenerator extends BeanClassGenerator {
    private static final String CONFIG_FILE = "ConvertModel";

    protected String getConfFile() {
        return CONFIG_FILE;
    }

    public String getFileName(Table table) {
        return "convert" + File.separator + NameUtils.getClassName(table.getTableName()) + "Convert.java";
    }
}
