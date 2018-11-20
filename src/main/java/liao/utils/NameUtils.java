package liao.utils;

import java.io.File;
import java.util.Properties;

/**
 * Created by ao on 2017/10/12.
 */
public class NameUtils {
    private static final Properties conf = PropertyUtils.getConfig("config");
    /**
     * 下划线转驼峰法
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String underline2Camel(String line){
        if(line==null||"".equals(line)){
            return "";
        }

        String[] codes = line.split("_");
        String result = codes[0];
        for(int i = 1;i < codes.length;i++){
            result+=codes[i].substring(0,1).toUpperCase() + codes[i].substring(1);
        }
        return result;
    }

    public static String getSetterMethodName(String name){
        return getMethodName("set",name);
    }
    public static String getGetterMethodName(String name,String type){
        String pre = "get";
        if("BOOLEAN".equals(type.toUpperCase())){
            pre = "is";
        }
        return getMethodName(pre,name);
    }
    private static String getMethodName(String pre,String name){
        String methodName = underline2Camel(name);
        methodName = firstCharUpper(methodName);
        return pre+methodName;
    }
    public static String getClassName(String tableName){
        String className = underline2Camel(tableName.replace(conf.getProperty("tablePre"),""));
        return firstCharUpper(className);
    }

    public static String getAliasName(String tableName){
        String className = underline2Camel(tableName.replace(conf.getProperty("tablePre"),""));
        return underline2Camel(className);
    }

    private static String firstCharUpper(String name){
        return name.substring(0,1).toUpperCase()+name.substring(1);
    }

}
