package liao.code.generator.back.factory;

import liao.code.generator.AbstractCodeGenerator;

/**
 * Created by ao on 2017/10/24.
 */
public interface Factory<T extends AbstractCodeGenerator> {
    public T create();
}
