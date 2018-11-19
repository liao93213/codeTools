package liao.code.generator.page.enums;

import liao.utils.EnumCheck;

/**
 * Created by ao on 2017/10/25.
 */
public enum NullableEnum implements EnumCheck {
    YES(1,"是","notNull"),
    NO(0,"否",null);
    private int value;
    private String desc;
    private String checkMethod;
    NullableEnum(int value, String desc, String checkMethod) {
        this.value = value;
        this.desc = desc;
        this.checkMethod = checkMethod;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public String getCheckMethod() {
        return checkMethod;
    }
}
