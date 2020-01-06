package liao.parse.table.mysql;

import liao.code.generator.AbstractCodeGenerator;
import liao.code.generator.back.factory.RegistrationFactory;
import liao.parse.table.model.Column;
import liao.parse.table.model.Table;
import liao.utils.ParseDDLUtils;

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
                tableList.add(ParseDDLUtils.parseDDLSQL(ddlSql));
                ddlSql = new ArrayList<String>();
            }
            if(line.trim().equalsIgnoreCase("##")){
                break;
            }
        }
        sc.close();
        List<AbstractCodeGenerator> generatorList = RegistrationFactory.getGeneratorList();
        for(Table table : tableList) {
            for (AbstractCodeGenerator classGenerator : generatorList) {
                classGenerator.generatorCode(table);
            }
        }
    }

}
