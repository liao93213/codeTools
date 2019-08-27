package liao.code.generator.back.factory;

import liao.code.generator.AbstractCodeGenerator;
import liao.code.generator.back.javacode.*;
import liao.code.generator.back.sql.SqlGenerator;

/**
 * Created by ao on 2017/10/24.
 */
public enum GeneratorTypeEnum {
    PO(1,new BeanClassGenerator()),
    DOMAIN(2,new DomianModelClassGenerator()),
    PARAMETER(3,new ParameterModelClassGenerator()),
    REQ(4,new RequestModelClassGenerator()),
    RESP(5,new RespModelClassGenerator()),
    RESULT(6,new ResultModelClassGenerator()),
    EXAMPLE(7,new ExampleModelClassGenerator()),
    DAO(8,new DaoClassGenerator()),
    SERVICE(10,new ServiceClassGenerator()),
    SERVICE_IMPL(11,new ServiceImplClassGenerator()),
    CONTROLLER(12,new ControllerClassGenerator()),
    SQL(13,new SqlGenerator()),
    MANAGER(14,new MangerClassGenerator()),
    MANAGER_IMPL(15,new ManagerImplClassGenerator());
    private int type;
    private AbstractCodeGenerator generator;

    GeneratorTypeEnum(int type, AbstractCodeGenerator abstractCodeGenerator) {
        this.type = type;
        this.generator = abstractCodeGenerator;
    }

    public AbstractCodeGenerator getGenerator() {
        return generator;
    }
}
