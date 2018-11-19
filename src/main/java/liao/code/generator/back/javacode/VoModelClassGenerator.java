package liao.code.generator.back.javacode;

import liao.code.generator.back.factory.Factory;
import liao.parse.table.model.Table;
import liao.utils.NameUtils;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by ao on 2017/10/23.
 */
@Component
public class VoModelClassGenerator extends AbstractClassGenerator{
    private static final String CONFIG_FILE = "VoModel";

    @Override
    protected String getConfFile() {
        return CONFIG_FILE;
    }

    @Override
    protected String getFileName(Table table) {
        return "vo" + File.separator + NameUtils.getClassName(table.getTableName()) + "Vo.java";
    }
    public static class VoFactory implements Factory<VoModelClassGenerator> {

        @Override
        public VoModelClassGenerator create() {
            return new VoModelClassGenerator();
        }
    }
}
