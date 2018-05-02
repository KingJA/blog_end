package com.kingja.blog.dao;

import com.kingja.blog.entity.Article;
import com.kingja.blog.dto.ArticleDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2018/4/3 11:25
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Transactional
public interface ArticleDao extends JpaRepository<Article, Integer> {

    public List<Article> findByTitleLike(String keyword);

    public List<Article> findByContentLike(String keyword);

    public List<Article> findByCatalogid(Integer catelogId);

    @Query("select new com.kingja.blog.dto.ArticleDTO(article.id,catalog.name, article.title,article.content,article" +
            ".published, article.createtime) FROM Article article left join Catalog catalog ON article" +
            ".catalogid=catalog.id")
    List<ArticleDTO> findArticleItem();

}
