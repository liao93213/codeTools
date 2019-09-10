package liao.code.generator.back.javacode;



import liao.parse.table.model.Column;
import liao.parse.table.model.Table;
import liao.utils.NameUtils;

import java.io.File;
import java.util.List;

/**
 * Created by liao on 2019/8/27.
 */
public class RespModelClassGenerator extends BeanClassGenerator {
    private static final String CONFIG_FILE = "RespModel";

    public String getFileName(Table table) {
        return "model" + File.separator + "response" + File.separator + NameUtils.getClassName(table.getTableName()) + "Response.java";
    }

    protected StringBuilder createAttr(Table table){
        List<Column> colList = table.getColumnList();
        StringBuilder content = new StringBuilder();
        for(Column col : colList){
            content.append("    @ApiModelProperty(value = \""+col.getComment()+"\")");
            content.append(System.lineSeparator());
            content.append("    private "+ col.getColJavaType() + " " + col.getCamelColName() + ";//"+col.getComment()+System.lineSeparator());
        }
        return content;
    }
    @Override
    protected String getConfFile() {
        return CONFIG_FILE;
    }

}
