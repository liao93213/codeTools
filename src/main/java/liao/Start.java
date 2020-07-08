package liao;

import liao.code.generator.AbstractCodeGenerator;
import liao.code.generator.back.javacode.AbstractClassGenerator;
import liao.code.generator.back.factory.RegistrationFactory;
import liao.code.generator.back.sql.SqlGenerator;
import liao.parse.table.model.Table;
import liao.parse.table.mysql.ParseTableForMySQL;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Created by ao on 2017/10/16.
 */
public class Start {
    /*public static void  main(String[] args){
        System.out.println("输入表名称：");
        Scanner sc = new Scanner(System.in);
        String tableName = sc.nextLine().trim();
        Table table = new ParseTableForMySQL(tableName).getTable();
        List<AbstractCodeGenerator> generatorList = RegistrationFactory.getGeneratorList();
        for(AbstractCodeGenerator classGenerator : generatorList){
            classGenerator.generatorCode(table);
        }
    }*/
    private static Date getHour(int hour){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.HOUR_OF_DAY,hour);
        Date date = cal.getTime();
        return date;
    }


    public static void main(String[] args){
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        Date fourteen = getHour(14);
        Date sixteen = getHour(16);
        System.out.println(now.compareTo(fourteen) > 0);
        System.out.println(now.compareTo(sixteen) > 0);
    }
}
