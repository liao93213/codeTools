package liao.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cheng on 2020/1/10.
 */
public class PatternUtils {
    public static String group(int i,String regex,String input){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        matcher.find();
        return matcher.group(i);
    }

    public static String replaceAll(String input,String... regexs){
        for(String regex : regexs){
            input = input.replaceAll(regex,"");
        }
        return input;
    }
}
