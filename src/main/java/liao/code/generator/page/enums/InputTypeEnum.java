package liao.code.generator.page.enums;

import liao.utils.EnumValue;
import liao.utils.PropertyUtils;

import java.util.Properties;

/**
 * Created by ao on 2017/10/25.
 */
public enum InputTypeEnum implements EnumValue{
    INPUT_TEXT(1,"输入框","getElementById") {
        @Override
        public String htmlModel() {
            return pro.getProperty("input_text_model");
        }
    },
    RADIO(2,"单选按钮","getRadioValue") {
        @Override
        public String htmlModel() {
            return pro.getProperty("radio_model");
        }
    },
    CHECKBOX(3,"复选框","getCheckBoxValues") {
        @Override
        public String htmlModel() {
            return pro.getProperty("checkbox_model");
        }
    },
    TEXT(4,"纯文本","") {
        @Override
        public String htmlModel() {
            return pro.getProperty("text_model");
        }
    },
    SELECT(5,"下拉类别","getElementById") {
        @Override
        public String htmlModel() {
            return pro.getProperty("select_model");
        }
    },
    DATE(6,"日期控件","getElementById") {
        @Override
        public String htmlModel() {
            return pro.getProperty("date_html_model");
        }
    },
    DATE_TIME(7,"时间控件","getElementById") {
        @Override
        public String htmlModel() {
            return pro.getProperty("datetime_html_model");
        }
    };
    private int value;
    private String desc;
    private String getValueMethod;
    private static Properties pro = PropertyUtils.getConfig("htmlModel");
    InputTypeEnum(int value, String desc,String getValueMethod) {
        this.value = value;
        this.desc = desc;
        this.getValueMethod = getValueMethod;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public String getGetValueMethod() {
        return getValueMethod;
    }
    public abstract String htmlModel();
}
