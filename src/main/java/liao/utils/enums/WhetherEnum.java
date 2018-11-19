package liao.utils.enums;

import liao.utils.EnumValue;

/**
 * Created by ao on 2017/10/27.
 */
public enum WhetherEnum implements EnumValue {
    NO(0, "否", "N"),
    YES(1, "是", "Y");

    private int value;
    private String desc;
    private String alias;

    private WhetherEnum(int index, String name, String alias) {
        this.value = index;
        this.desc = name;
        this.alias = alias;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}