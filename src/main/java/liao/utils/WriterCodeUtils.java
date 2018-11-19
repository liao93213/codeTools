package liao.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by ao on 2017/10/16.
 */
public class WriterCodeUtils {
    public static final String CODE_PATH = PropertyUtils.getConfig("config").getProperty("codePath");
    public static void writeCode(String fileName,String content){
        try {
            createNewFile(fileName);
            try(BufferedWriter writer  = new BufferedWriter(new FileWriter(CODE_PATH + fileName))) {
                writer.write(content);
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void createNewFile(String fileName) throws IOException {
        File file = new File(CODE_PATH+fileName.substring(0,fileName.lastIndexOf(File.separator)));
        file.mkdirs();
        file = new File(CODE_PATH + fileName);
        if(!file.exists()){
            file.createNewFile();
        }
    }
}
