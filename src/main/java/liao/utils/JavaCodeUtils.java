package liao.utils;

import liao.parse.table.model.Column;
import liao.parse.table.model.Table;

import java.io.File;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by cheng on 2019/9/21.
 */
public class JavaCodeUtils {
    private static final Pattern METHOD = Pattern.compile(".+([a-zA-Z0-9]+ *){2,4}\\(([a-zA-Z0-9<>,]| )+.*;?");
    private static final Pattern JAVA_PROPERTY = Pattern.compile(" *((private)|(protected)|(public))? *([a-zA-Z0-9<>]+ *){2};.*");
    private static final String ACCESS_PERMISSION = "private|protected|public";
    private static final Pattern COMMENT = Pattern.compile("//");
    private static final  Pattern CLASS_LAST_LINE = Pattern.compile("^ *}.*");
    private static final Properties properties = PropertyUtils.getConfig("config");
    private static final String MVC_NAME = properties.getProperty("mvc");

    public static Column parseToColumn(String line){

        String propertyName = PatternUtils.group(2,"(.+) *([a-zA-Z0-9-_$]+);",line);
        String javaType = PatternUtils.group(1," *(.+) *(.+);",line);
        String comment = PatternUtils.replaceAll(line,".+;","//","/\\*\\*","/\\*","\\*/");
        Column column = new Column();
        column.setCamelColName(propertyName);
        column.setColJavaType(javaType);
        column.setColName(propertyName);
        column.setComment(comment);
        return column;
    }
    public static boolean isJavaProperty(String line){
        return JAVA_PROPERTY.matcher(line).matches();
    }
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
        Matcher matcher = Pattern.compile("( .+)( .+;)").matcher(code);
        matcher.find();
        String propertyName = matcher.group(2);
        return propertyName.replaceAll("^ ","").equals(code);
    }

    public static StringBuilder getColMethodDefine(List<Column> colList){
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

    public static String interfaceToMethod(String interfaceDefine,String alais,String suffix){
        interfaceDefine = interfaceDefine.replaceAll("^ +","");
        String[] defines = interfaceDefine.split(" ");
        if(!ACCESS_PERMISSION.contains(defines[0])){//没有访问权限
            interfaceDefine = "public " + interfaceDefine;
        }else{//有访问权限，替换为public
            interfaceDefine = "public" + interfaceDefine.substring(defines[0].length());
        }
        interfaceDefine = interfaceDefine.replaceAll("^ +","");
        interfaceDefine = interfaceDefine.replaceAll(" *;"," {");
        //String[] defines = interfaceDefine.split(" +");
        String returnType = returnType(interfaceDefine);
        String methodName = methodName(interfaceDefine);
        String callParam = callParam(interfaceDefine);
        StringBuilder method = new StringBuilder();
        method.append("    @Override");
        method.append(System.lineSeparator());
        method.append("    "+interfaceDefine);
        method.append(System.lineSeparator());
        method.append("        ");
        if(!returnType.equals("void")) {
            method.append(returnType);
            method.append(" result = ");
        }
        method.append(alais+NameUtils.getClassName(suffix));
        method.append(".");
        method.append(methodName);
        method.append("(");
        method.append(callParam);
        method.append(")");

        method.append(";"+System.lineSeparator());
        if(!returnType.equals("void")) {
            method.append("        return result;");
        }
        method.append(System.lineSeparator());
        method.append("    }");
        return method.toString();
    }
    private static String returnType(String interfaceDefine){
        Matcher matcher = Pattern.compile("( .+)( .+\\()").matcher(interfaceDefine);
        matcher.find();
        String returnType = matcher.group(1);
        return returnType.replaceAll("^ ","");
    }

    private static String methodName(String interfaceDefine){
        Matcher matcher = Pattern.compile("( .+)( .+\\()").matcher(interfaceDefine);
        matcher.find();
        String methodName = matcher.group(2);
        return methodName.replace("(","").replaceAll("^ *","");
    }
    private static String callParam(String interfaceDefine){
        Matcher matcher = Pattern.compile("(\\(.+\\))").matcher(interfaceDefine);
        matcher.find();
        String callParam = matcher.group();
        callParam = callParam.replace("(","");
        callParam = callParam.replace(")","");
        String[] params =  callParam.split(",");
        StringBuilder paramBuilder = new StringBuilder();
        for(int i = 0;i < params.length;i++){
            params[i] = params[i].replaceAll(" *$","");//移除逗号前的空格
            params[i] =  params[i].replaceAll(".+ +","");//移除类型
            paramBuilder.append(params[i]);
        }
        return paramBuilder.toString();
    }

    public static int getClassLastLine(List<String> codes){
        for(int i = codes.size() - 1; i > 0;i--){
            Matcher matcher = CLASS_LAST_LINE.matcher(codes.get(i));
            if(matcher.matches()){
                return i;
            }
        }
        throw new IllegalArgumentException("不是合法的class文件");
    }

    public static List<String> getMvcName(String javaClassName){
        String[] names = MVC_NAME.split("\\|");
        javaClassName = new File(javaClassName).getName();
        javaClassName = javaClassName.replaceAll("Impl\\.java","");
        for(int i = 0;i < names.length;i++){
            if(javaClassName.endsWith(names[i])){
                if(i > 0){
                    return Stream.of(names[i-1],names[i]).collect(Collectors.toList());
                }else{
                    return Stream.of(names[i]).collect(Collectors.toList());
                }
            }
        }
        throw new IllegalArgumentException();
    }

    public static boolean isImpl(String javaClassName){
        javaClassName = new File(javaClassName).getName();
        javaClassName = javaClassName.replaceAll("\\.java","");
        return javaClassName.endsWith("Impl") || javaClassName.equals("Controller");
    }


    public static void main(String[] args){
        System.out.println(parseToColumn("private StringBuilder createAttr;//IIAAAA"));
        System.out.println(interfaceToMethod("private StringBuilder createAttr(List<Column> colList);","Order","manager"));
    }
}
