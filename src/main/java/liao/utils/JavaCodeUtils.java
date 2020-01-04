package liao.utils;

import liao.parse.table.model.Column;
import liao.parse.table.model.Table;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by cheng on 2019/9/21.
 */
public class JavaCodeUtils {
    private static final Pattern METHOD = Pattern.compile(".+([a-zA-Z0-9]+ *){2,4}\\(([a-zA-Z0-9]| )++");
    private static final Pattern COMMENT = Pattern.compile("//");
    public static boolean isMethod(String line){
        return METHOD.matcher(line).matches();
    }

    public static boolean isSetMethod(String code,String setMethodName){
        return code.contains(setMethodName);
    }

    public static boolean isGetMethod(String code,String getMethodName){
        return code.contains(getMethodName);
    }

    public static boolean isThisCol(String code,String colName){
        code = code.replaceAll(";.+","");
        code = code.replaceAll("^ +","");
        String[] codes = code.split(" ");
        if(codes.length < 3){
            return false;
        }
        return codes[2].equals(colName);
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

    public static String getGetMethod(Column col){
        StringBuilder content = new StringBuilder();
        String setModel = NameUtils.getSetterMethodName(col.getCamelColName());
        content.append("    public void " +setModel + "(" +col.getColJavaType()+" "+ col.getCamelColName()+ "){"+System.lineSeparator());
        content.append("        this."+col.getCamelColName()+" = "+col.getCamelColName()+";"+System.lineSeparator());
        content.append("    }"+System.lineSeparator());
        return content.toString();
    }

    public static String setGetMethod(Column col){
        StringBuilder content = new StringBuilder();
        String getMethod = NameUtils.getGetterMethodName(col.getCamelColName(),col.getColJavaType());
        content.append("    public "+ col.getColJavaType() + " " +getMethod + "(){"+System.lineSeparator());
        content.append("        return "+col.getCamelColName()+";"+System.lineSeparator());
        content.append("    }"+System.lineSeparator());
        return content.toString();
    }

    public static StringBuilder createAttr(Table table){
        List<Column> colList = table.getColumnList();
        StringBuilder content = new StringBuilder();
        for(Column col : colList){
            content.append("    private "+ col.getColJavaType() + " " + col.getCamelColName() + ";//"+col.getComment()+System.lineSeparator());
        }
        return content;
    }

    public static StringBuilder createAttr(List<Column> colList){
        StringBuilder content = new StringBuilder();
        for(Column col : colList){
            content.append("    private "+ col.getColJavaType() + " " + col.getCamelColName() + ";//"+col.getComment()+System.lineSeparator());
        }
        return content;
    }

    public String interfaceToClassMethod(String interfaceMethod){
        String classMethod = interfaceMethod.replaceAll(";","");
        return classMethod + " {"+System.lineSeparator()+"##"+System.lineSeparator() + "    }";
    }



    public static String createOneAttr(Column col){
        StringBuilder content = new StringBuilder();
        content.append("    private "+ col.getColJavaType() + " " + col.getCamelColName() + ";//"+col.getComment()+System.lineSeparator());
        return content.toString();
    }


    public static void main(String[] args){
        System.out.println(isMethod("static StringBuilder createAttr("));
        System.out.println(isMethod("private static StringBuilder createAttr;"));
    }
}
