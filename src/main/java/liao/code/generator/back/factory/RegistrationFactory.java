package liao.code.generator.back.factory;

import liao.code.generator.AbstractCodeGenerator;
import liao.code.generator.back.javacode.AbstractClassGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ao on 2017/10/24.
 */
public class RegistrationFactory {
    public static List<AbstractCodeGenerator> getGeneratorList(){
        List<AbstractCodeGenerator> resultList = new ArrayList<>();
        for(GeneratorTypeEnum type : GeneratorTypeEnum.values()){
            resultList.add(type.getFactory().create());
        }
        return resultList;
    }
}
