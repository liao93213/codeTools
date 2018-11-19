package liao.code.generator;

import liao.parse.table.model.Table;
import liao.utils.PropertyUtils;
import liao.utils.ReaderUtils;
import liao.utils.WriterCodeUtils;

/**
 * Created by ao on 2017/10/25.
 */
public abstract class AbstractCodeGenerator {
    protected abstract String replaceModelCode(Table table, String model);
    protected abstract String getConfFile();
    protected abstract String getFileName(Table table);
    public void generatorCode(Table table) {
        PropertyUtils.getConfig("config").getProperty("basePackage");
        String model = ReaderUtils.getModel(getConfFile());
        model = replaceModelCode(table,model);
        model = model.replaceAll("#basePackage#", PropertyUtils.getConfig("config").getProperty("basePackage"));
        model = model.replaceAll("#className#", table.getClassName());
        model = model.replaceAll("#alias#", table.getAlias());
        model = model.replaceAll("#tableComment#", table.getComment());
        String fileName = getFileName(table);
        WriterCodeUtils.writeCode(fileName,model);
    }
}
