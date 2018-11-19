package liao.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ao on 2017/10/16.
 */
public class ReaderUtils {
    public static String getModel(String modelName){
        try {
            return  readAllModelLines("model/" + modelName + ".txt");
        } catch (IOException e) {
            throw new RuntimeException(modelName+"文件不存在");
        }
    }
    public static String readAllModelLines(String path) throws IOException {
        StringBuilder modelText = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(ReaderUtils.class.getClassLoader().getResourceAsStream(path)))) {
            String line = null;
            while((line = reader.readLine()) != null){
                modelText.append(line + System.lineSeparator());
            }
        }
        return modelText.toString();
    }
    public static List<String> readAllLines(String path) throws IOException {
        List<String> modelText = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path),"UTF-8"))) {
            String line = null;
            while((line = reader.readLine()) != null){
                modelText.add(line);
            }
        }
        return modelText;
    }
}
