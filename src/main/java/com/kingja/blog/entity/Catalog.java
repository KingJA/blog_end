package com.kingja.blog.entity;

import com.kingja.blog.util.DateUtil;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Description：TODO
 * Create Time：2018/4/3 11:21
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @CreatedDate
    private Date createtime;
    @LastModifiedDate
    private Date updatetime;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
