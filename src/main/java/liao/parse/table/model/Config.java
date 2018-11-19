package liao.parse.table.model;

/**
 * Created by ao on 2017/10/24.
 */
public class Config {
    private String tableNamePre;
    private String url;
    private String password;
    private String userName;
    private String codePath;
    private String hideHTML;

    public String getTableNamePre() {
        return tableNamePre;
    }

    public void setTableNamePre(String tableNamePre) {
        this.tableNamePre = tableNamePre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCodePath() {
        return codePath;
    }

    public void setCodePath(String codePath) {
        this.codePath = codePath;
    }

    public String getHideHTML() {
        return hideHTML;
    }

    public void setHideHTML(String hideHTML) {
        this.hideHTML = hideHTML;
    }
}
