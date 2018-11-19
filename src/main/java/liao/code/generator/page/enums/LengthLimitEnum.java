package liao.code.generator.page.enums;

import liao.utils.EnumCheck;

/**
 * Created by ao on 2017/10/25.
 */
public enum LengthLimitEnum implements EnumCheck {
    LENGTH(1,"限制","checkMethod","maxlength");
    private int value;
    private String desc;
    private String checkMethod;
    private String htmlAttr;
    LengthLimitEnum(int value,
             String desc,
             String checkMethod,
             String htmlAttr){
        this.value = value;
        this.desc = desc;
        this.checkMethod = checkMethod;
        this.htmlAttr = htmlAttr;
    }

    @Override
    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public String getCheckMethod() {
        return checkMethod;
    }

    public String getHtmlAttr() {
        return htmlAttr;
    }
}
