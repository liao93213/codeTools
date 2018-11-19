package liao.code.generator.page;

import liao.code.generator.page.model.Page;
import liao.code.generator.page.model.PageTable;
import liao.parse.table.model.Table;
import liao.utils.ReaderUtils;
import liao.utils.WriterCodeUtils;

import java.util.List;

/**
 * Created by ao on 2017/11/16.
 */
public abstract class AbstractPageCodeGenerator {
    protected abstract String replaceModelCode(PageTable table, List<Table> tableList, String model);
    protected abstract String getConfFile();
    protected abstract String getFileName(Table table);
    public void generatorCode(PageTable pageTable, List<Table> table) {
    }
}
