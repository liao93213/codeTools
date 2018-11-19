package liao.parse.table.model;

/**
 * Created by ao on 2017/10/13.
 */
public class Column {
    private String colName;
    private String camelColName;
    private String colDBType;
    private String colJavaType;
    private String comment;
    private int isNullable;
    private String tableName;

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getCamelColName() {
        return camelColName;
    }

    public void setCamelColName(String camelColName) {
        this.camelColName = camelColName;
    }

    public String getColDBType() {
        return colDBType;
    }

    public void setColDBType(String colDBType) {
        this.colDBType = colDBType;
    }

    public String getColJavaType() {
        return colJavaType;
    }

    public void setColJavaType(String colJavaType) {
        this.colJavaType = colJavaType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int isNullable() {
        return isNullable;
    }

    public void setNullable(int nullable) {
        isNullable = nullable;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
