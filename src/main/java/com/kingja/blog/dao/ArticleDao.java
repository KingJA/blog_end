package com.kingja.blog.dao;

import com.kingja.blog.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Description：TODO
 * Create Time：2018/4/3 11:25
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Transactional
public interface ArticleDao extends JpaRepository<Article, Integer> {

}
