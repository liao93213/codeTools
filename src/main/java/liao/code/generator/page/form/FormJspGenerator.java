package liao.code.generator.page.form;

import liao.code.generator.AbstractCodeGenerator;
import liao.code.generator.page.enums.InputTypeEnum;
import liao.code.generator.page.enums.LengthLimitEnum;
import liao.code.generator.page.enums.ValueTypeEnum;
import liao.code.generator.page.model.Element;
import liao.code.generator.page.model.Page;
import liao.code.generator.page.model.PageTable;
import liao.parse.table.model.Table;
import liao.utils.CommonUtils;
import liao.utils.PropertyUtils;
import liao.utils.enums.WhetherEnum;

import java.io.File;
import java.util.List;
import java.util.Properties;

/**
 * Created by ao on 2017/10/25.
 */
public class FormJspGenerator extends AbstractCodeGenerator{
    private static final Properties pro = PropertyUtils.getConfig("htmlModel");
    private static final String TABLE_MODEL = pro.getProperty("table_model");
    private static final String TR_MODEL =pro.getProperty("tr_model");
    private static final String TD_LIST_MODEL = pro.getProperty("td_list_model");
    private static final String TD_RIGHT_MODEL = pro.getProperty("td_right_model");
    private static final String TD_LEFT_MODEL = pro.getProperty("td_left_model");
    private static final String TD_LIST_HEAD_MODEL = pro.getProperty("td_list_head_model");
    private static final String TR_LIST_HEAD_MODEL = pro.getProperty("tr_list_head_model");
    private static final String TR_LIST_MODEL = pro.getProperty("tr_list_model");



    protected String replaceModelCode(Table table, Page page, String model) {
        if(page.getPageTableList().isEmpty()){
            return "";
        }
        StringBuilder htmlBody = new StringBuilder();
        for(PageTable pageTable : page.getPageTableList()){
            if(pageTable.getIsList() != WhetherEnum.YES.getValue()){
                htmlBody.append(createTable(pageTable));
            }else{
                htmlBody.append(createListTable(pageTable));
            }
        }
        return replaceContent(TABLE_MODEL,htmlBody.toString());
    }

    private String createTable(PageTable table){
        String tableBody = createTableBody(table.getTdNumRow(),table.getElementList());
        String tableHTML = TABLE_MODEL.replace("#title#",table.getTableName());
        tableHTML = replaceContent(tableHTML,tableBody);
        return tableHTML;
    }
    private String createTableBody(int tdNumRow,List<Element> elementList){
        if(elementList.isEmpty()){
            return "";
        }
        StringBuilder body = new StringBuilder();
        StringBuilder trContent = new StringBuilder();
        int tdCount = 1;
        for(int i = 0;i < elementList.size();i++){
            Element element = elementList.get(i);
            String leftContent = replaceContent(TD_LEFT_MODEL,element.getEleName());
            String rightContent = replaceContent(TD_RIGHT_MODEL,createTdContent(element));
            trContent.append(leftContent);
            trContent.append(System.lineSeparator());
            trContent.append(rightContent);
            trContent.append(System.lineSeparator());

            if(tdCount == tdNumRow || i == elementList.size()-1){
                body.append(replaceContent(TR_MODEL,trContent.toString()));
                body.append(System.lineSeparator());
                trContent = new StringBuilder();
                tdCount = 1;
            }
        }
        return body.toString();
    }
    private String createListTable(PageTable table){
        StringBuilder tableContent = new StringBuilder();
        tableContent.append(createListTableHead(table.getElementList()));
        tableContent.append(createListTableBody(table.getElementList()));
        String tableHTML = TABLE_MODEL.replaceAll("#title#",table.getTableName());
        return replaceContent(tableHTML,tableContent.toString());
    }

    private String createListTableHead(List<Element> elementList){
        if(elementList.isEmpty()){
            return "";
        }
        StringBuilder head = new StringBuilder();
        for(Element element : elementList){
            head.append(replaceContent(TD_LIST_HEAD_MODEL,element.getColName()));
            head.append(System.lineSeparator());
        }
        return replaceContent(TR_LIST_HEAD_MODEL,head.toString());
    }
    private String createListTableBody(List<Element> elementList){
        if(elementList.isEmpty()){
            return "";
        }
        String textModel = InputTypeEnum.TEXT.htmlModel();
        StringBuilder body = new StringBuilder();
        for(Element element : elementList){
            textModel = replaceModel(textModel,element);
            body.append(replaceContent(TD_LIST_MODEL,textModel));
        }
        String trModel = TR_LIST_MODEL.replace("#beanName#",elementList.get(0).getBeanName());
        trModel = replaceContent(trModel,body.toString());
        return trModel;
    }
    private String createTdContent(Element element){
        InputTypeEnum inputType = CommonUtils.getEnumByValue(element.getInputType(),InputTypeEnum.values());
        ValueTypeEnum valueType = CommonUtils.getEnumByValue(element.getTypeLimit(),ValueTypeEnum.values());
        String model = inputType.htmlModel();
        if(inputType == InputTypeEnum.INPUT_TEXT){
            int i = model.indexOf(">");
            String head = model.substring(0,i);
            String tail = model.substring(i);
            if(!CommonUtils.isEmpty(valueType.getBlurCheckMethod())) {
                head +="onblur=\""+valueType.getBlurCheckMethod()+"\"";
            }
            if(element.getLengthLimit() != null && element.getLengthLimit() > 0){
                head += LengthLimitEnum.LENGTH.getHtmlAttr()+"=\""+element.getLengthLimit()+"\"";
            }
            model = head + tail;
        }

        return replaceModel(model,element);
    }

    private String replaceModel(String model,Element element){
        model = model.replaceAll("#beanName#",element.getBeanName());
        model = model.replaceAll("#eleName#",element.getEleName());
        return model;
    }
    private String replaceContent(String model,String content){
        return model.replaceAll("#content#",content);
    }

    @Override
    protected String replaceModelCode(Table table, String model) {
        return null;
    }

    @Override
    protected String getConfFile() {
        return "FormJsp";
    }

    @Override
    protected String getFileName(Table table) {
        return "view"+File.separator+table.getAlias()+"Form.jsp";
    }
}