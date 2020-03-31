package liao.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Created by cheng on 2020/1/8.
 */
public class FileUtils {
    private static final Properties properties = PropertyUtils.getConfig("config");
    private static final String BEAN_DIR = properties.getProperty("base_dir");

    public static List<String> searchJavaFileByNameRegex(String className, String classNameRegex) {
        return searchJavaFileByNameRegex(className, classNameRegex, true);
    }

    public static List<String> searchJavaFileByNameRegex(String className, String classNameRegex, boolean needEquals) {
        List<String> fileNameList = new ArrayList<>();
        WriterCodeUtils.listFileName(BEAN_DIR, fileNameList);
        Pattern BEAN_REGEX = Pattern.compile(classNameRegex.replace("##", className));
        List<String> resultList = new ArrayList<>();
        for (String fileName : fileNameList) {
            String[] dirs = fileName.split("\\\\");
            String javaFileName = dirs[dirs.length - 1];
            if (!javaFileName.endsWith(".java")) {
                continue;
            }
            javaFileName = javaFileName.replace(".java", "");
            if ((needEquals && javaFileName.equals(className)) || (javaFileName.contains(className) && BEAN_REGEX.matcher(javaFileName).matches())) {
                resultList.add(fileName);
            }
        }

        return resultList;
    }

    public static void main(String[] args){
        List<String> needWriteFileNameList = FileUtils.searchJavaFileByNameRegex("DispatchTask","(^##Dao)|(^##Manager)|(^##Service)|(^##Controller)|(^##DaoImpl)|(^##ManagerImpl)|(^##ServiceImpl)|(^##ControllerImpl)",false);
        System.out.println(needWriteFileNameList);
    }


}