package com.kingja.blog.dto;

import com.kingja.blog.util.DateUtil;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description：TODO
 * Create Time：2018/4/3 11:21
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Data
public class ArticleDTO implements Serializable {
    private int id;
    private int published;
    private String name;
    private String title;
    private String content;
    private Date createtime;

    public ArticleDTO(int id, String name, String title, String content,int published, Date createtime) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.content = content;
        this.published = published;
        this.createtime = createtime;
    }

    public String getCreatetime() {
        return DateUtil.getStringDate(createtime);
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public ArticleDTO() {
    }
}
