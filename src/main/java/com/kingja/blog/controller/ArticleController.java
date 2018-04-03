package com.kingja.blog.controller;

import com.kingja.blog.dao.ArticleDao;
import com.kingja.blog.entity.Article;
import com.kingja.blog.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2018/4/3 11:16
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@RestController
@RequestMapping(value = "/api/article")
public class ArticleController {

    @Autowired
    private ArticleDao articleDao;

    @CrossOrigin
    @PostMapping(value = "/add")
    public Result addArticle(Article article) {
        System.out.println("title:"+article.getTitle());
        System.out.println("content:"+article.getContent());
        articleDao.save(article);
        return new Result(0, "添加成功", article);

    }

    @CrossOrigin
    @PostMapping(value = "/all")
    public Result getArticles() {
        List<Article> articles = articleDao.findAll();
        return new Result(0, "获取文章成功", articles);

    }
}
