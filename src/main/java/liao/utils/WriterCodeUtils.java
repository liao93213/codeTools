package liao.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

    public static List<String> listFileName(String dir) {
        File[] files = listFile(dir);
        List<String> fileNames = new ArrayList<>();
        for (File file : files) {
            fileNames.add(file.getAbsolutePath());
        }
        return fileNames;
    }

    public static File[] listFile(String dir) {
        File file = new File(dir);
        return file.listFiles();
    }

    public static void modifyCode(String fileName,String content){
        try {
            clearFile(fileName);
            try(BufferedWriter writer  = new BufferedWriter(new FileWriter(fileName))) {

                writer.write(content);
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearFile(String fileName){
        File file =new File(fileName);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(listFileName("E:\\\\gitwork\\\\basic-ofc\\\\basic-ofc\\\\basic-ofc-domain\\\\src\\\\main\\\\java\\\\com\\\\basic\\\\ofc\\\\domain\\\\"));
        System.out.println(Files.readAllLines(Paths.get("E:\\\\gitwork\\\\basic-ofc\\\\basic-ofc\\\\basic-ofc-domain\\\\src\\\\main\\\\java\\\\com\\\\basic\\\\ofc\\\\domain\\\\UpdateDeliveryDateMessage.java")));
    }
}
