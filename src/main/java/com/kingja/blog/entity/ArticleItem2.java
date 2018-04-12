package com.kingja.blog.entity;

import com.kingja.blog.util.DateUtil;

import java.util.Date;

/**
 * Description：TODO
 * Create Time：2018/4/3 11:21
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ArticleItem2 {
    private int id;
    private String catalogname;
    private String title;
    private String content;
    private Date createtime;

    public String getCatalogname() {
        return catalogname;
    }

    public void setCatalogname(String catalogname) {
        this.catalogname = catalogname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatetime() {
        return DateUtil.getStringDate(createtime) ;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
