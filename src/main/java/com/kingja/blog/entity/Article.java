package com.kingja.blog.entity;

import com.kingja.blog.util.DateUtil;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Description：TODO
 * Create Time：2018/4/3 11:21
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private String content;
    @CreatedDate
    private Date createtime;
    @LastModifiedDate
    private Date updatetime;

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

    public String getUpdatetime() {
        return DateUtil.getStringDate(updatetime) ;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}
