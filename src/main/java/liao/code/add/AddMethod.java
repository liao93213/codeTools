package liao.code.add;

import liao.utils.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created by cheng on 2020/1/4.
 */
public class AddMethod {
    private static final Properties properties = PropertyUtils.getConfig("config");
    private static final String MVC_REGEX = properties.getProperty("mvc_regex");

    public static void main(String[] args) throws IOException {
        System.out.println("没有格式化的代码请谨慎使用,mapper文件修改使用ParseMySQLDDL");
        System.out.println("请输入表名：");
        Scanner sc = new Scanner(System.in);
        Method method = null;
        while (sc.hasNext()) {
            String line = sc.nextLine();
            if (line == null || line.trim().length() == 0) {
                continue;
            } else {
                method = new Method(NameUtils.getClassName(line.trim()));
                break;
            }
        }
        System.out.println("新增的方法定义：");
        while (sc.hasNext()) {
            String line = sc.nextLine();
            if(line.trim().length() == 0){
                continue;
            }
            if ("##".equals(line.trim())) {
                break;
            }else if(JavaCodeUtils.isMethod(line.trim())){//方法定义部分
                method.setInterfaceDefine("    " + line.trim());
            }else{//注释部分
                method.setComment(method.getComment() == null ? "    " + line.trim()+System.lineSeparator() : method.getComment() + "    " + line.trim()+System.lineSeparator());
            }
        }
        sc.close();
        writeMethod(method);
    }

    public static void writeMethod(Method method) throws IOException {
        List<String> needWriteFileNameList = FileUtils.searchJavaFileByNameRegex(method.getClassName(),MVC_REGEX,false);
        for(String fileName : needWriteFileNameList){
            List<String> lineList = Files.readAllLines(Paths.get(fileName));
            boolean isImpl = JavaCodeUtils.isImpl(fileName);
            String methodCode = method.getInterfaceDefine();
            if(isImpl){
                String suffix = JavaCodeUtils.getMvcName(fileName).get(0);
                String comment = StringUtils.isBlank(method.getComment()) ? "" : method.getComment();
                methodCode = comment + JavaCodeUtils.interfaceToMethod(method.getInterfaceDefine(), NameUtils.getAliasName(method.getClassName()),suffix);
            }
            int classLastLineNum = JavaCodeUtils.getClassLastLine(lineList) - 1;
            List<String> resultCodeList = new ArrayList<>();
            for (int i = 0; i < lineList.size(); i++) {
                resultCodeList.add(lineList.get(i));
                resultCodeList.add(System.lineSeparator());
                if(i == classLastLineNum){
                    resultCodeList.add(methodCode);
                    resultCodeList.add(System.lineSeparator());
                }
            }
            System.out.println("写入："+fileName);
            WriterCodeUtils.modifyCode(fileName, CommonUtils.contactCollectionWithToken(resultCodeList, ""));
        }
    }
}
