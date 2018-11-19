package liao.code.generator.page.backCode.java;

import liao.code.generator.back.factory.Factory;
import liao.code.generator.back.javacode.AbstractClassGenerator;
import liao.parse.table.model.Table;
import liao.utils.NameUtils;

import java.io.File;

/**
 * Created by ao on 2017/10/23.
 */
public class ServiceImplClassGenerator  extends AbstractClassGenerator{
    private static final String CONFIG_FILE = "ServiceImpl";

    @Override
    protected String getConfFile() {
        return CONFIG_FILE;
    }

    @Override
    protected String getFileName(Table table) {
        return "service"+ File.separator+"impl"+ File.separator+ NameUtils.getClassName(table.getTableName())+"ServiceImpl.java";
    }
    public static class ServiceImplFactory implements Factory<ServiceImplClassGenerator> {

        @Override
        public ServiceImplClassGenerator create() {
            return new ServiceImplClassGenerator();
        }
    }
}
