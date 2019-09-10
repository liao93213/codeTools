package liao.code.generator.back.javacode;



import liao.parse.table.model.Table;
import liao.utils.NameUtils;

import java.io.File;

/**
 * Created by ao on 2017/10/23.
 */
public class DaoClassGenerator extends AbstractClassGenerator {
    private static final String CONFIG_FILE = "IDao";

    @Override
    protected String getConfFile() {
        return CONFIG_FILE;
    }

    @Override
    protected String getFileName(Table table) {
        return "dao" + File.separator + "api" + File.separator + NameUtils.getClassName(table.getTableName()) + "Dao.java";
    }
}
