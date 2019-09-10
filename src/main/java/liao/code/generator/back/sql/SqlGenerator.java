package liao.code.generator.back.sql;

import liao.code.generator.AbstractCodeGenerator;
import liao.parse.table.model.Column;
import liao.parse.table.model.Table;
import liao.utils.NameUtils;
import liao.utils.PropertyUtils;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by ao on 2017/10/13.
 */
@Component
public class SqlGenerator extends AbstractCodeGenerator {
    private static final String CONFIG_FILE = "sqlModel";

    public String replaceModelCode(Table table, String model) {
        String selectSql = createSelectSql(table);
        String insertSql = createInsertSql(table);
        String updateSql = createUpdateSql(table);
        String resultMap = createResultMap(table);
        String columns = getColumns(table);
        String batchInsert = createBatchInsertSql(table);
        model = model.replace("#tableName#", table.getTableName());
        model = model.replace("#selectSQL#", selectSql);
        model = model.replace("#insertSQL#", insertSql);
        model = model.replace("#resultMap#", resultMap);
        model = model.replace("#columns#", columns);
        model = model.replace("#saveAll#", batchInsert);
        model = model.replace("#firstColName#",table.getColumnList().get(0).getColName());
        model = model.replace("#firstColCamelName#",table.getColumnList().get(0).getCamelColName());
        return model.replace("#updateSQL#", updateSql);
    }

    public String createSelectSql(Table table) {
        StringBuilder sql = new StringBuilder("SELECT" + System.lineSeparator());
        sql.append("        <include refid=\"columns\"/>" + System.lineSeparator());
        sql.append("        FROM " + table.getTableName() + " t");
        return sql.toString();
    }

    public String createResultMap(Table table) {
        StringBuilder sql = new StringBuilder();
        for (Column col : table.getColumnList()) {
            sql.append("        <result property=\"" + col.getCamelColName() + "\" column=\"" + col.getColName() + "\"/>" + System.lineSeparator());
        }
        sql = removeCharAndLater(sql,System.lineSeparator());
        return sql.toString();
    }

    public String createInsertSql(Table table) {
        StringBuilder sql = new StringBuilder("INSERT INTO " + table.getTableName() + "(" + System.lineSeparator());
        for (Column col : table.getColumnList()) {
            sql.append("            " + col.getColName() + "," + System.lineSeparator());
        }
        sql = removeLastChar(sql, ",");
        sql.append("        )" + System.lineSeparator());
        sql.append("        VALUES(" + System.lineSeparator());
        for (Column col : table.getColumnList()) {
            sql.append("            #{" + col.getCamelColName() + "," + getJdbcType(col.getColDBType()) + "}," + System.lineSeparator());
        }
        sql = removeLastChar(sql, ",");
        sql.append("        )");
        return sql.toString();
    }

    public String createBatchInsertSql(Table table) {
        StringBuilder sql = new StringBuilder("INSERT INTO " + table.getTableName() + "(" + System.lineSeparator());
        sql.append(getColumns(table));
        sql.append("        )" + System.lineSeparator());
        sql.append("        VALUES" + System.lineSeparator());
        sql.append("        <foreach collection=\"collection\" item=\"item\" open=\"(\" close=\")\" separator=\"),(\">" + System.lineSeparator());
        for (Column col : table.getColumnList()) {
            sql.append("            #{item." + col.getCamelColName() + "," + getJdbcType(col.getColDBType()) + "}," + System.lineSeparator());
        }
        sql = removeLastChar(sql, ",");
        sql.append("        </foreach>");
        return sql.toString();
    }

    public String getColumns(Table table) {
        StringBuilder sql = new StringBuilder();
        for (Column col : table.getColumnList()) {
            sql.append("            " + col.getColName() + "," + System.lineSeparator());
        }
        sql = removeCharAndLater(sql, ",");
        return sql.toString();
    }

    public String getJdbcType(String dbType) {
        if (dbType.toLowerCase().contains("tinyint") || dbType.toLowerCase().contains("smallint")
                || dbType.toLowerCase().contains("int") || dbType.toLowerCase().contains("bigint")) {
            return "jdbcType=NUMERIC";
        } else if (dbType.toLowerCase().contains("decimal")) {
            return "jdbcType=NUMERIC";
        } else if (dbType.toLowerCase().contains("varchar")) {
            return "jdbcType=VARCHAR";
        } else if (dbType.toLowerCase().contains("datetime") || dbType.toLowerCase().contains("date")) {
            return "jdbcType=DATE";
        }
        return "jdbcType=VARCHAR";
    }

    public String createUpdateSql(Table table) {
        StringBuilder sql = new StringBuilder("UPDATE " + table.getTableName() + System.lineSeparator());
        sql.append("       <set>" + System.lineSeparator());
        for (Column col : table.getColumnList()) {
            sql.append("          <if test=\"" + col.getCamelColName() + " != null\">" + System.lineSeparator());
            sql.append("              " + col.getColName() + " = #{" + col.getCamelColName() + "," + getJdbcType(col.getColDBType()) + "}," + System.lineSeparator());
            sql.append("          </if>" + System.lineSeparator());
        }
        sql = removeLastChar(sql, ",");
        sql.append("       </set>" + System.lineSeparator());
        sql.append("        WHERE "+table.getColumnList().get(0).getColName()+"=#{"+table.getColumnList().get(0).getCamelColName()+"}");
        return sql.toString();
    }

    public StringBuilder removeLastChar(StringBuilder str, String code) {
        return new StringBuilder(str.substring(0, str.lastIndexOf(code)) + str.substring(str.lastIndexOf(code)+1));
    }

    public StringBuilder removeCharAndLater(StringBuilder str, String code) {
        return new StringBuilder(str.substring(0, str.lastIndexOf(code)));
    }

    public String getFileName(Table table) {
        return "sql" + File.separator + NameUtils.underline2Camel(table.getTableName().replace(PropertyUtils.getConfig("config").getProperty("tablePre"), "")) + "Mapper.xml";
    }

    @Override
    protected String getConfFile() {
        return CONFIG_FILE;
    }
}
