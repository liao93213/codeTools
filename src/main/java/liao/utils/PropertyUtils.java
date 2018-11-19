package liao.utils;

import java.io.*;
import java.util.Properties;

/**
 * Created by ao on 2017/10/16.
 */
public class PropertyUtils {
    public static Properties getConfig(String fileName){
        Properties pro = new Properties();
        try(Reader in = new InputStreamReader(PropertyUtils.class.getResourceAsStream("/"+fileName+".properties"),"utf-8")) {
            pro.load(in);
            return pro;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
