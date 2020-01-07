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
        System.out.println("没有格式化的代码请谨慎使用,mapper文件修改使用ParseMySQLDDL");
        System.out.println("新加字段或者字段名称修改时表现为新增，类型或者注释修改时会在原基础上修改");
        System.out.println("可输入java字段定义，也可以输入ddl语句");
        System.out.println("请输入表名或者类型名称：");
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
            Column column = null;
            if(JavaCodeUtils.isJavaProperty(line)){//如果是非数据库增加字段
                column = JavaCodeUtils.parseToColumn(line);
            }else {
                column = ParseDDLUtils.getOneColumn(line, table.getTableName());
            }
            columnList.add(column);
        }
        table.setColumnList(columnList);
        writeBean(table);
        sc.close();
    }

    public static void writeBean(Table table) throws IOException {
        List<String> needWriteFileNameList = getNeedWriteBeanName(table.getClassName(),BEAN_NAME_REGEX);
        for (String fileName : needWriteFileNameList) {
            List<String> lineList = Files.readAllLines(Paths.get(fileName));
            //先执行修改字段，移除需要修改的字段相应的代码
            modifyColumn(lineList,table.getColumnList());
            String attrCode = JavaCodeUtils.createAttr(table.getColumnList()).toString();
            String methodCode = JavaCodeUtils.getColMethodDefine(table.getColumnList()).toString();
            boolean needMethod = needMethod(lineList);
            int attrNum = getLastAttrNum(lineList);
            List<String> resultCodeList = new ArrayList<>();
            for (int i = 0; i < lineList.size(); i++) {
                if (attrNum == i) {
                    resultCodeList.add(lineList.get(i));
                    resultCodeList.add(System.lineSeparator());
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

    private static void modifyColumn(List<String> allCodeList,List<Column> columnList){
        List<Column> needAddColumnList = new ArrayList<>(columnList);
        Iterator<Column> columnIterator = needAddColumnList.iterator();
        while(columnIterator.hasNext()){
            Column column = columnIterator.next();
            String getMethodName = NameUtils.getGetterMethodName(column.getCamelColName(),column.getColJavaType());
            String setMethodName = NameUtils.getSetterMethodName(column.getCamelColName());
            Iterator<String> codeIterator = allCodeList.iterator();
            while(codeIterator.hasNext()){
                String lineCode = codeIterator.next();
                if(JavaCodeUtils.isGetMethod(lineCode,getMethodName)){
                    codeIterator.remove();//删除需要修改的代码
                    codeIterator.next();
                    codeIterator.remove();
                    codeIterator.next();
                    codeIterator.remove();
                }else if(JavaCodeUtils.isSetMethod(lineCode,setMethodName)){
                    codeIterator.remove();
                    codeIterator.next();
                    codeIterator.remove();
                    codeIterator.next();
                    codeIterator.remove();
                }else if(JavaCodeUtils.isThisCol(lineCode,column.getCamelColName())){
                    codeIterator.remove();//删除需要修改的字段
                }
            }

        }
    }

    private static int getLastAttrNum(List<String> lineList) throws IOException {
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

    public static List<String> getNeedWriteBeanName(String className,String classNameRegex) {
        List<String> fileNameList = new ArrayList<>();
        WriterCodeUtils.listFileName(BEAN_DIR,fileNameList);
        Pattern BEAN_REGEX = Pattern.compile(classNameRegex.replace("##",className));
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
