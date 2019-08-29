package liao.code.generator.back.javacode.test;

import liao.code.generator.AbstractCodeGenerator;
import liao.parse.table.model.Column;
import liao.parse.table.model.Table;
import liao.utils.CommonUtils;
import liao.utils.NameUtils;

import java.io.File;

/**
 * Created by cheng on 2019/8/29.
 */
public class ServiceTestClassGenerator extends AbstractCodeGenerator {
    private static final String CONFIG_FILE = "ServiceTest";

    public String replaceModelCode(Table table, String model) {
        String createTestData11 = createTestData(table, 1);
        String createTestData12 = createTestData(table, 2);
        model = model.replace("#createTestData1#", createTestData11);
        model = model.replace("#createTestData2#", createTestData12);
        return model;
    }

    private String createTestData(Table table, int index) {
        StringBuilder code = new StringBuilder("        " + table.getClassName() + "Parameter " + table.getAlias() + "Parameter = " + "new " + table.getClassName() + "Parameter();");
        code.append(System.lineSeparator());
        for (int order = 0; order < table.getColumnList().size(); order++) {
            Column col = table.getColumnList().get(order);
            String data = CommonUtils.createNewJavaObject(col.getColJavaType(), order * index);
            code.append("        " + table.getAlias() + "Parameter." + NameUtils.getSetterMethodName(col.getColName()) + "(" + data + "); ");
            code.append(System.lineSeparator());
        }
        code.append("       return " + table.getAlias() + "Parameter;");
        return code.toString();
    }

    @Override
    protected String getConfFile() {
        return CONFIG_FILE;
    }

    @Override
    protected String getFileName(Table table) {
        return "test" + File.separator + NameUtils.getClassName(table.getTableName()) + "ServiceImplTest.java";
    }
}
