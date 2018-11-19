package liao.parse.table.mysql;

import liao.code.generator.AbstractCodeGenerator;
import liao.code.generator.back.factory.RegistrationFactory;
import liao.parse.table.model.Column;
import liao.parse.table.model.Table;
import liao.utils.CommonUtils;
import liao.utils.NameUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by ao on 2017/10/13.
 */
public class ParseMySQLDDL {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        List<String> ddlSql = new ArrayList<String>();
        List<Table> tableList = new ArrayList<>();
        while (sc.hasNext()){
            String line = sc.nextLine().trim();
            if(line.isEmpty()){//空行忽略
                continue;
            }
            ddlSql.add(line);
            if(line.substring(0,1).equals(")")){//最后一行
                tableList.add(parseDDLSQL(ddlSql));
                ddlSql = new ArrayList<String>();
            }
            if(line.trim().equals("the end")){
                break;
            }

        }
        List<AbstractCodeGenerator> generatorList = RegistrationFactory.getGeneratorList();
        for(Table table : tableList) {
            for (AbstractCodeGenerator classGenerator : generatorList) {
                classGenerator.generatorCode(table);
            }
        }
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
                String comment = getComment(oneLine);
                comment = comment.replaceAll("表$","");
                table.setComment(comment);
            }
        }
        return table;
    }
    private static Column getOneColumn(String oneLine,String tableName){
        oneLine = oneLine.replaceAll("`|'|,","");
        String[] eles = oneLine.split(" ");
        String colName = eles[0];
        String colType = eles[1];
        String colComment = eles[eles.length-1];
        String camelColName = NameUtils.underline2Camel(colName);//转成驼峰命名
        String colJavaType = CommonUtils.sqlTypeToJavaType(colType);
        Column col = new Column();
        col.setColName(colName);
        col.setCamelColName(camelColName);
        col.setColDBType(colType);
        col.setColJavaType(colJavaType);
        col.setComment(colComment);
        col.setTableName(tableName);
        return col;
    }

    private static String getComment(String line){
        line = line.replaceAll("`|'|,|;","");
        String[] eles = line.split(" ");
        for(int i = 0;i < eles.length; i++) {
            if(eles[i].toLowerCase().startsWith("comment")){
                return eles[i].split("=")[1];
            }
        }
        return "";
    }
    private static String getTableName(String firstLine){
        String tableName = firstLine.substring(firstLine.indexOf("`")+1,firstLine.lastIndexOf("`"));
        return tableName;
    }
}
