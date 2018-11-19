package liao;

import liao.code.generator.AbstractCodeGenerator;
import liao.code.generator.back.javacode.AbstractClassGenerator;
import liao.code.generator.back.factory.RegistrationFactory;
import liao.code.generator.back.sql.SqlGenerator;
import liao.parse.table.model.Table;
import liao.parse.table.mysql.ParseTableForMySQL;

import java.util.List;
import java.util.Scanner;

/**
 * Created by ao on 2017/10/16.
 */
public class Start {
    public static void  main(String[] args){
        System.out.println("输入表名称：");
        Scanner sc = new Scanner(System.in);
        String tableName = sc.nextLine().trim();
        Table table = new ParseTableForMySQL(tableName).getTable();
        List<AbstractCodeGenerator> generatorList = RegistrationFactory.getGeneratorList();
        for(AbstractCodeGenerator classGenerator : generatorList){
            classGenerator.generatorCode(table);
        }
    }
}
