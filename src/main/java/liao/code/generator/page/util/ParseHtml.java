package liao.code.generator.page.util;

import liao.code.generator.page.enums.InputTypeEnum;
import liao.code.generator.page.model.Element;
import liao.code.generator.page.model.Page;
import liao.code.generator.page.model.PageTable;
import liao.utils.PropertyUtils;
import liao.utils.ReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ao on 2017/10/17.
 */
public class ParseHtml {
    private static final String HANZI = "[\\u4e00-\\u9fa5]";
    private static final String NOT_PLAIN_TEXT = "=\\s*[\"|'][^>]*"+HANZI+"[^>]*[\"|']";
    private static final String GET_TEXT = "(<[^<]*>)?([^>]*"+HANZI+"+[^<]*)";
    private static final String HANZI_BEFORE = "..*>";
    private static final String HANZI_AFTER = "：|：";
    private static final String COMMENT = "<!--.+\\n*\\r*|-->";
    private static final String HTML_HEAD = ".+<\\s*body\\s*>";
    private static final String HTML_TAIL ="<\\s*/\\s*body\\s*>.*";
    private static final String IGNORE_TYPE = "(<\\s*option.+>)|(<\\s*input.+>)";
    private static final String SELECT_TYPE = "("+HANZI+"+)";
    private static final String HAS_TAG = "<|[</][^>]+>";
    private static final String HIDE = PropertyUtils.getConfig("config").getProperty("hide");
    private static final String SPLIT = PropertyUtils.getConfig("htmlParse").getProperty("split");
    private static final Pattern HIDE_PATTERN = Pattern.compile("(.*)(<[^<]*"+HIDE+".+)");
    private static final String SPECIAL_CHAR = "只|个|元|分|角|查看|清空";
    private static final Pattern GET_HANZI_PATTERN = Pattern.compile(GET_TEXT);
    private static final Pattern HANZI_PATTERN = Pattern.compile(HANZI);
    private static final Pattern HTML_HEAD_PATTERN = Pattern.compile(HTML_HEAD);
    private static final Pattern HTML_TAIL_PATTERN = Pattern.compile(HTML_TAIL);
    private static final Pattern HANZI_BEFORE_PATTERN = Pattern.compile(HANZI_BEFORE);
    private static final Pattern HANZI_AFTER_PATTERN = Pattern.compile(HANZI_AFTER);
    private static final Pattern HAS_TAG_PATTERN = Pattern.compile(HAS_TAG);


    public static Page getAllElement(String path){
        Page page = new Page();
        try {
            List<String> htmlLines = ReaderUtils.readAllLines(path);
            PageTable pageTable = new PageTable();
            List<Element> elementList = new ArrayList<>();
            pageTable.setElementList(elementList);
            for(int i = 0;i < htmlLines.size();){
                String line = htmlLines.get(i);
                Matcher matcher = HIDE_PATTERN.matcher(line);
                if(matcher.find()){//如果是影藏的部分，不解析直接跳过
                    String needParseHtml = matcher.group(2);
                    line = matcher.group(1);//防止代码开头有匹配的元素
                    htmlLines.set(i,needParseHtml);//直解析需要忽略的部分
                    i = getMatchEndTagIndex(i,htmlLines);//找到下一个需要开始解析的位置
                }else{
                    i++;
                }
                elementList.addAll(getElementNameList(line));

            }
            List<PageTable> pageTableList = new ArrayList<>(1);
            pageTableList.add(pageTable);
            page.setPageTableList(pageTableList);
            return page;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> splitIfFormula(String eleStr){
        return null;
    }

    /**
     * 找到影藏部分起始标签来确定下一个解析的部分
     * @param nowIndex
     * @param htmlLines
     * @return
     */
    private static int getMatchEndTagIndex(int nowIndex,List<String> htmlLines){
        String startLine = htmlLines.get(nowIndex);
        String tagName = getTagName(startLine);
        startLine = startLine.substring(startLine.indexOf("<" + tagName)+("<" + tagName).length());
        htmlLines.set(nowIndex,startLine);
        int startTagCount = 1;
        for(int i = nowIndex;i < htmlLines.size();i++){
            String html = htmlLines.get(i);
            Result parseResult = noMatchTagCount(startTagCount,tagName,html);
            startTagCount = parseResult.noMatchCount;
            if(startTagCount == 0){
                htmlLines.set(i,parseResult.noParseHtml);
                return i;
            }
        }
        return htmlLines.size()-1;
    }

    /**
     * 递归查找出所有的匹配标签，请从1开始
     * @param startTagCount
     * @param tagName
     * @param html
     * @return
     */
    private static Result noMatchTagCount(int startTagCount,String tagName,String html){
        for(;;) {
            int startTagIndex = html.indexOf("<" + tagName);
            int endTagIndex = html.indexOf("</" + tagName);
            if((startTagIndex < 0 && endTagIndex < 0) || startTagCount==0){
                return new Result(startTagCount,html);
            }
            else if (endTagIndex < 0 || (startTagIndex > 0 && endTagIndex > startTagIndex)) {
                startTagCount++;
                html = html.substring(html.indexOf("<" + tagName)+("<" + tagName).length());
            }
            else if (startTagIndex < 0 ||(endTagIndex > 0 && endTagIndex < startTagIndex)  ) {
                startTagCount--;
                html = html.substring(html.indexOf("</" + tagName)+("</" + tagName).length());
            }
        }
    }
    private static String getTagName(String html){
        if(!hasTag(html)){
            return null;
        }
        html = html.trim();
        String[] attr =  html.split(" ");
        attr[0] = attr[0].substring(1);
        return attr[0];
    }
    private static String getEndTag(String html){
        if( html.contains("</")){
            html= html.replace(".*</","");
            html = html.trim();
            String[] attr =  html.split(" ");
            return attr[0];
        }
        return null;
    }
    private static boolean isEndTag(String html){
        return html.contains("</");
    }
    private static boolean hasTag(String html){
        Matcher matcher = HAS_TAG_PATTERN.matcher(html);
        return matcher.find();
    }
    private static List<Element> getElementNameList(String line){
        List<Element> elementList = new ArrayList<>();
        line = line.replaceAll(NOT_PLAIN_TEXT,""); //去掉非纯文本的汉字
        line = line.replaceAll(COMMENT,""); //去掉注释
        Matcher matcher =GET_HANZI_PATTERN.matcher(line);
        while (matcher.find()){
            String type = matcher.group(1);
            String name = matcher.group(2);
            name = name.replaceAll(HANZI_BEFORE,"");
            name = name.replaceAll(HANZI_AFTER,"");
            name = name.trim();
            if(type != null && !type.toLowerCase().matches(IGNORE_TYPE) && !SPECIAL_CHAR.contains(name)) {
                String[] names = name.split(SPLIT);
                for(int i = 0;i < names.length;i++) {
                    String n = names[i];
                    //补全特许符号
                    if(i < names.length -1){
                        int endIndex = name.indexOf(names[i+1]);
                        int startIndex = name.indexOf(n);
                        n = name.substring(startIndex,endIndex);
                    }
                    Element ele = new Element();
                    ele.setEleName(n);
                    ele.setInputType(getInputType(line));
                    elementList.add(ele);
                }
            }
        }
        return elementList;
    }

    private static List<String> formatHtml(List<String> htmlLines){
        return htmlLines;
    }
    private static List<String> removeHtmlHeadAndTail(List<String> htmlLines){
       for(String lines : htmlLines){

       }
        return htmlLines;
    }
    private static String getTitle(List<String> htmlLines){
        return "";
    }

    private static boolean htmlTypeNeedIgnore(String line){
        return false;
    }
    private static void getTableTag(List<String> htmlLines){

    }

    private static Integer getInputType(String line){
        return InputTypeEnum.INPUT_TEXT.getValue();
    }
    private static List<String> getValueList(List<String> htmlLines,int index){
        return null;
    }

    static class Result{
        int noMatchCount;
        String noParseHtml;
        Result( int noMatchCount,
                String noParseHtml){
            this.noMatchCount = noMatchCount;
            this.noParseHtml = noParseHtml;
        }
    }
    public static void main(String[] args){
    }

}
