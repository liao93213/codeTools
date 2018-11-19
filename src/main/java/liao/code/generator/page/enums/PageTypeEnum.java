package liao.code.generator.page.enums;

import liao.utils.EnumValue;

/**
 * Created by ao on 2017/11/3.
 */
public enum PageTypeEnum implements EnumValue{
    BROWSER(1,"浏览"),
    FORM(2,"表单");
    PageTypeEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
    private int value;
    private String desc;
    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
