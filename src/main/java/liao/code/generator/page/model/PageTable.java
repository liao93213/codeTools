package liao.code.generator.page.model;

import java.util.List;

/**
 * Created by ao on 2017/10/17.
 */
public class PageTable {
    private String tableName;
    private int isList = 0;
    private int tdNumRow;
    private List<Element> elementList;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Element> getElementList() {
        return elementList;
    }

    public void setElementList(List<Element> elementList) {
        this.elementList = elementList;
    }

    public int getIsList() {
        return isList;
    }

    public void setIsList(int isList) {
        this.isList = isList;
    }

    public int getTdNumRow() {
        return tdNumRow;
    }

    public void setTdNumRow(int tdNumRow) {
        this.tdNumRow = tdNumRow;
    }
}
