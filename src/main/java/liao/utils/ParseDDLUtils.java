package liao.utils;

import liao.parse.table.model.Column;
import liao.parse.table.model.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2019/9/21.
 */
public class ParseDDLUtils {

    public static String sqlTypeToJavaType(String sqlType){
        sqlType = sqlType.replaceAll("\\(.+\\)","").toUpperCase();//去掉括号
        if(sqlType.equals("BIGINT")){
            return "Long";
        }else if(sqlType.equals("INT") || sqlType.equals("SMALLINT") || sqlType.equals("TINYINT")){
            return "Integer";
        }else if (sqlType.equals("CHAR") || sqlType.equals("VARCHAR") || sqlType.equals("TEXT") && sqlType.equals("LONGTEXT")){
            return "String";
        }else if(sqlType.equals("DATE") || sqlType.equals("DATETIME")){
            return "Date";
        }else if(sqlType.equals("DECIMAL")){
            return "BigDecimal";
        }else if(sqlType.equals("TIMESTAMP")){
            return "Long";
        }
        return sqlType;
    }

    public static String getComment(String line,boolean isRow){
        line = line.replaceAll("`|'|,|;","");
        String[] eles = line.split(" ");
        for(int i = 0;i < eles.length; i++) {
            if(eles[i].toLowerCase().startsWith("comment")){
                if(isRow){
                    return eles[i+1];
                }else {
                    return eles[i].split("=")[1];
                }
            }
        }
        return "";
    }

    public static Column getOneColumn(String oneLine, String tableName){
        if(oneLine.trim().isEmpty()){
            return  null;
        }
        oneLine = oneLine.replaceAll("`|'|,","");
        String[] eles = oneLine.split(" ");
        String colName = eles[0];
        String colType = eles[1];
        String colComment = getComment(oneLine,true);
        String camelColName = NameUtils.underline2Camel(colName);//转成驼峰命名
        String colJavaType = sqlTypeToJavaType(colType);
        Column col = new Column();
        col.setColName(colName);
        col.setCamelColName(camelColName);
        col.setColDBType(colType);
        col.setColJavaType(colJavaType);
        col.setComment(colComment);
        col.setTableName(tableName);
        return col;
    }


    public static String getTableName(String firstLine){
        String tableName = firstLine.substring(firstLine.indexOf("`")+1,firstLine.lastIndexOf("`"));
        return tableName;
    }

    public static Table parseDDLSQL(List<String> sqlList){
        String tableName = getTableName(sqlList.get(0));
        Table table = new Table(tableName);
        List<Column> columnList = new ArrayList<Column>();
        table.setColumnList(columnList);
        for(String oneLine : sqlList){
            if(oneLine.substring(0,1).equals("`")){//是字段定义sql
                columnList.add(getOneColumn(oneLine,tableName));
            }
            if(oneLine.substring(0,1).equals(")")){
                String comment = getComment(oneLine,false);
                comment = comment.replaceAll("表$","");
                table.setComment(comment);
            }
        }
        return table;
    }
}
