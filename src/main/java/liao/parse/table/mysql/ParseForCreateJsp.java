package liao.parse.table.mysql;

import liao.parse.table.model.Table;
import liao.utils.PropertyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class ParseForCreateJsp {
    private static final Properties pro = PropertyUtils.getConfig("htmlModel");
    private static final String TABLE_MODEL = pro.getProperty("table_model");
    private static final String TR_MODEL =pro.getProperty("tr_model");
    private static final String TD_LIST_MODEL = pro.getProperty("td_list_model");
    private static final String TD_RIGHT_MODEL = pro.getProperty("td_right_model");
    private static final String TD_LEFT_MODEL = pro.getProperty("td_left_model");
    private static final String TD_LIST_HEAD_MODEL = pro.getProperty("td_list_head_model");
    private static final String TR_LIST_HEAD_MODEL = pro.getProperty("tr_list_head_model");
    private static final String TR_LIST_MODEL = pro.getProperty("tr_list_model");
    public void createJSP(List<Table> tableList){
        System.out.println("开始生成JSP：");
        Scanner sc = new Scanner(System.in);
        List<String> tableRow = new ArrayList<String>();
        System.out.println("表格名称：");
        String tableName = sc.nextLine().trim();
        while (sc.hasNext()){

        }
    }
}
