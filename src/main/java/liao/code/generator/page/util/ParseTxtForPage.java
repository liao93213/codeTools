package liao.code.generator.page.util;

import liao.code.generator.page.model.Element;
import liao.code.generator.page.model.Page;
import liao.code.generator.page.model.PageTable;
import liao.utils.ReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Created by ao on 2017/11/9.
 */
public class ParseTxtForPage {
    public static Page getAllElement(String path){
        Page page = new Page();
        try {
            List<String> htmlLines = ReaderUtils.readAllLines(path);
            PageTable pageTable = new PageTable();
            List<Element> elementList = new ArrayList<>();
            pageTable.setElementList(elementList);
            for(int i = 0;i < htmlLines.size();i++){
                String line = htmlLines.get(i);
                Element element = new Element();
                element.setEleName(line);
                elementList.add(element);
            }
            List<PageTable> pageTableList = new ArrayList<>(1);
            pageTableList.add(pageTable);
            page.setPageTableList(pageTableList);
            return page;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
