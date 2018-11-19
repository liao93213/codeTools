package liao.code.generator.back.javacode;

import liao.code.generator.AbstractCodeGenerator;
import liao.parse.table.model.Table;
import org.springframework.stereotype.Component;

/**
 * Created by ao on 2017/10/24.
 */
@Component
public abstract class AbstractClassGenerator extends AbstractCodeGenerator {
    protected String createCode(Table table){
        return "";
    }
    protected abstract String getFileName(Table table);
    protected String replaceModelCode(Table table, String model){
        String content = createCode(table);
        return model.replace("#content#",content);
    }

}
