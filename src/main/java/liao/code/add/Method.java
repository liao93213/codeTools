package liao.code.add;

/**
 * Created by cheng on 2020/1/21.
 */
public class Method {
    private String className;
    private String comment;
    private String interfaceDefine;

    public Method(String className) {
        this.className = className;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getInterfaceDefine() {
        return interfaceDefine;
    }

    public void setInterfaceDefine(String interfaceDefine) {
        this.interfaceDefine = interfaceDefine;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
