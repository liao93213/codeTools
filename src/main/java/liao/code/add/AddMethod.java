package liao.code.add;

import liao.parse.table.model.Column;
import liao.parse.table.model.Table;
import liao.utils.ParseDDLUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by cheng on 2020/1/4.
 */
public class AddMethod {

    public static void main(String[] args) throws IOException {
        System.out.println("没有格式化的代码请谨慎使用,mapper文件修改使用ParseMySQLDDL");
        System.out.println("请输入表名：");
        Scanner sc = new Scanner(System.in);
        Table table = null;
        while (sc.hasNext()) {
            String line = sc.nextLine();
            if (line == null || line.trim().length() == 0) {
                continue;
            } else {
                table = new Table(line.trim());
                break;
            }
        }
        System.out.println("新增的方法定义：");
        while (sc.hasNext()) {
            String line = sc.nextLine().trim();
            if ("##".equals(line)) {
                break;
            }
        }
        sc.close();
    }

    public static void getMethod(){

    }
}
