package com.kingja.blog.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * Description：TODO
 * Create Time：2018/4/3 11:21
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Data
public class ArticleItem implements Serializable {
    private int id;
    private String name;
    private String title;
    private String content;
    private Date createtime;

    public ArticleItem(int id, String name, String title, String content, Date createtime) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.content = content;
        this.createtime = createtime;
    }

    public ArticleItem() {
    }
}
