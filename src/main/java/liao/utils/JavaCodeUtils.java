package liao.utils;

import liao.parse.table.model.Column;
import liao.parse.table.model.Table;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by cheng on 2019/9/21.
 */
public class JavaCodeUtils {
    private static final Pattern METHOD = Pattern.compile(".+([a-zA-Z0-9]+ *){2,4}\\(.+");
    private static final Pattern COMMENT = Pattern.compile("//");
    public static boolean isMethod(String line){
        return METHOD.matcher(line).matches();
    }



    public static StringBuilder getMethodDefine(List<Column> colList){
        StringBuilder content = new StringBuilder();
        for(Column col : colList){
            String getMethod = NameUtils.getGetterMethodName(col.getCamelColName(),col.getColJavaType());
            String setModel = NameUtils.getSetterMethodName(col.getCamelColName());
            content.append("    public "+ col.getColJavaType() + " " +getMethod + "(){"+System.lineSeparator());
            content.append("        return "+col.getCamelColName()+";"+System.lineSeparator());
            content.append("    }"+System.lineSeparator());
            content.append("    public void " +setModel + "(" +col.getColJavaType()+" "+ col.getCamelColName()+ "){"+System.lineSeparator());
            content.append("        this."+col.getCamelColName()+" = "+col.getCamelColName()+";"+System.lineSeparator());
            content.append("    }"+System.lineSeparator());
        }
        return content;
    }

    public static StringBuilder createAttr(Table table){
        List<Column> colList = table.getColumnList();
        StringBuilder content = new StringBuilder();
        for(Column col : colList){
            content.append("    private "+ col.getColJavaType() + " " + col.getCamelColName() + ";//"+col.getComment()+System.lineSeparator());
        }
        return content;
    }


    public static void main(String[] args){
        System.out.println(isMethod("static StringBuilder createAttr("));
        System.out.println(isMethod("private static StringBuilder createAttr;"));
    }
}
