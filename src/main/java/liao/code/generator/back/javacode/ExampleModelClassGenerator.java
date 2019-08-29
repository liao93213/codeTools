package liao.code.generator.back.javacode;

import liao.parse.table.model.Table;
import liao.utils.NameUtils;

import java.io.File;

/**
 * Created by cheng on 2019/8/27.
 */
public class ExampleModelClassGenerator extends BeanClassGenerator {
    private static final String CONFIG_FILE = "ExampleModel";
    public String getFileName(Table table){
        return  "model"+ File.separator+"example"+ File.separator+ NameUtils.getClassName(table.getTableName())+"Example.java";
    }

    @Override
    protected String getConfFile() {
        return CONFIG_FILE;
    }

}
