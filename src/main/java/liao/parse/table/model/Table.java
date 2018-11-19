package liao.parse.table.model;

import liao.utils.NameUtils;

import java.util.List;

/**
 * Created by ao on 2017/10/13.
 */
public class Table {
    private String tableName;
    private String className;
    private String alias;
    private String comment;
    private List<Column> columnList;

    public Table(){

    }

    public Table(String tableName){
        this.tableName = tableName;
        this.className = NameUtils.getClassName(tableName);
        this.alias = NameUtils.getAliasName(tableName);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
