package liao.code.add;

import liao.parse.table.model.Column;
import liao.parse.table.model.Table;
import liao.utils.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by cheng on 2019/9/21.
 */
public class AddColumn {
    private static final Properties properties = PropertyUtils.getConfig("config");
    private static final String BEAN_DIR = properties.getProperty("base_dir");
    private static final String BEAN_NAME_REGEX = properties.getProperty("bean_name_regex");
    private static final Pattern BEAN_REGEX = Pattern.compile(BEAN_NAME_REGEX);

    public static void main(String[] args) throws IOException {
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
        System.out.println("请输入新增的字段：");
        List<Column> columnList = new ArrayList<>();
        while (sc.hasNext()) {
            String line = sc.nextLine().trim();
            if ("##".equals(line)) {
                break;
            }
            Column column = ParseDDLUtils.getOneColumn(line, table.getTableName());
            columnList.add(column);
        }
        table.setColumnList(columnList);
        writeBean(table);
        sc.close();
    }

    public static void writeBean(Table table) throws IOException {
        List<String> needWriteFileNameList = getNeedWriteBeanName(table.getClassName());
        String attrCode = JavaCodeUtils.createAttr(table).toString();
        String methodCode = JavaCodeUtils.getMethodDefine(table.getColumnList()).toString();
        for (String fileName : needWriteFileNameList) {
            List<String> lineList = Files.readAllLines(Paths.get(fileName));
            int attrNum = getAttrNum(lineList);
            boolean needMethod = needMethod(lineList);
            List<String> resultCodeList = new ArrayList<>();
            for (int i = 0; i < lineList.size(); i++) {
                if (attrNum == i) {
                    resultCodeList.add(lineList.get(i));
                    resultCodeList.add(attrCode);
                } else if (needMethod && i == lineList.size() - 1) {//最后一行
                    resultCodeList.add(methodCode);
                } else {
                    resultCodeList.add(lineList.get(i));
                    resultCodeList.add(System.lineSeparator());
                }

            }
            System.out.println("写入："+fileName);
            WriterCodeUtils.modifyCode(fileName, CommonUtils.contactCollectionWithToken(resultCodeList, ""));

        }
    }

    private static int getAttrNum(List<String> lineList) throws IOException {
        for (int lineNum = 0; lineNum < lineList.size(); lineNum++) {
            if (JavaCodeUtils.isMethod(lineList.get(lineNum))) {
                return lineNum-1;
            }
        }
        return lineList.size() - 2;
    }

    private static boolean needMethod(List<String> lineList) {
        for (int lineNum = 0; lineNum < lineList.size(); lineNum++) {
            if (JavaCodeUtils.isMethod(lineList.get(lineNum))) {
                return true;
            }
        }
        return false;
    }

    private boolean classLastLine(List<String> lineList) {
        for (int lineNum = 0; lineNum < lineList.size(); lineNum++) {
            if (JavaCodeUtils.isMethod(lineList.get(lineNum))) {
                return true;
            }
        }
        return false;
    }

    public static List<String> getNeedWriteBeanName(String className) {
        List<String> fileNameList = new ArrayList<>();
        WriterCodeUtils.listFileName(BEAN_DIR,fileNameList);

        List<String> resultList = new ArrayList<>();
        for (String fileName : fileNameList) {
            String[] dirs = fileName.split("\\\\");
            String javaFileName = dirs[dirs.length-1];
            if(!javaFileName.endsWith(".java")){
                continue;
            }
            javaFileName = javaFileName.replace(".java","");
            if (javaFileName.equals(className) || (javaFileName.contains(className) && BEAN_REGEX.matcher(javaFileName).matches())) {
                resultList.add(fileName);
            }
        }

        return resultList;

    }
}
