package liao.code.generator.back.javacode;



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
        content.append("");
        return content.toString();
    }
    protected StringBuilder createAttr(Table table){
        return JavaCodeUtils.createAttr(table);
    }


    public String getFileName(Table table){
        return  "model"+File.separator+"po"+ File.separator+NameUtils.getClassName(table.getTableName())+"PO.java";
    }

    @Override
    protected String getConfFile() {
        return CONFIG_FILE;
    }

}
