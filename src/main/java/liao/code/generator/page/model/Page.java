package liao.code.generator.page.model;

import java.util.List;

/**
 * Created by ao on 2017/10/25.
 */
public class Page {
    private String title;
    private List<PageTable> pageTableList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<PageTable> getPageTableList() {
        return pageTableList;
    }

    public void setPageTableList(List<PageTable> pageTableList) {
        this.pageTableList = pageTableList;
    }
}
