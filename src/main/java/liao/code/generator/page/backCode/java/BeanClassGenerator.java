package liao.code.generator.page.backCode.java;

import liao.code.generator.back.factory.Factory;
import liao.code.generator.back.javacode.AbstractClassGenerator;
import liao.parse.table.model.Column;
import liao.parse.table.model.Table;
import liao.utils.JavaCodeUtils;
import liao.utils.NameUtils;

import java.io.File;
import java.util.List;

/**
 * Created by ao on 2017/10/12.
 */
public class BeanClassGenerator extends AbstractClassGenerator {
    private static final String CONFIG_FILE = "PoModel";
    protected String createCode(Table table){
        StringBuilder content = createAttr(table);
        content.append(JavaCodeUtils.getColMethodDefine(table.getColumnList()));
        return content.toString();
    }
    private StringBuilder createAttr(Table table) {
        List<Column> colList = table.getColumnList();
        StringBuilder content = new StringBuilder();
        for (Column col : colList) {
            content.append("    private " + col.getColJavaType() + " " + col.getCamelColName() + ";//" + col.getComment() + System.lineSeparator());
        }
        return content;
    }

    public String getFileName(Table table){
        return  "model"+ File.separator+NameUtils.getClassName(table.getTableName())+".java";
    }

    @Override
    protected String getConfFile() {
        return CONFIG_FILE;
    }
}
