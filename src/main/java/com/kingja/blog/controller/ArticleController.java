package com.kingja.blog.controller;

import com.kingja.blog.dao.ArticleDao;
import com.kingja.blog.entity.Article;
import com.kingja.blog.entity.Result;
import com.kingja.blog.util.ConverUtil;
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
        System.out.println("title:" + article.getTitle());
        System.out.println("content:" + article.getContent());
        articleDao.save(article);
        return new Result(0, "添加成功", article);

    }

    @CrossOrigin
    @PostMapping(value = "/all")
    public Result getArticles() {
        List<Article> articles = articleDao.findAll();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Result(0, "获取文章成功", articles);
    }

    @CrossOrigin
    @GetMapping(value = "/get")
    public Result getArticleById(@RequestParam("id") String id) {
        Article article = articleDao.findById(Integer.valueOf(id)).get();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Result(0, "请求文章成功", article);
    }

    @CrossOrigin
    @PostMapping(value = "/modify")
    public Result modifyArticle(Article article) {
        Article exitArticle = articleDao.findById(article.getId()).get();
        exitArticle.setTitle(article.getTitle());
        exitArticle.setContent(article.getContent());
        articleDao.saveAndFlush(exitArticle);
        return new Result(0, "修改成功", null);
    }

    @CrossOrigin
    @PostMapping(value = "/delete")
    public Result deleteArticle(@RequestParam("id") String id) {
        articleDao.deleteById(Integer.valueOf(id));
        return new Result(0, "删除成功", null);
    }

    @CrossOrigin
    @PostMapping(value = "/query")
    public Result<List<Article>> query(@RequestParam("type") String type, @RequestParam("keyword") String keyword) {
        keyword = "%" +keyword+ "%";
        List<Article> results;
        if ("title".equals(type)) {
            results = articleDao.findByTitleLike(keyword);
        } else {
            results = articleDao.findByContentLike(keyword);
        }
        return new Result<>(0, "查询成功", results);
    }

    @CrossOrigin
    @PostMapping(value = "/published")
    public Result setPublishedStatus(@RequestParam("id") String id) {
        Article modifyedArticle = articleDao.findById(Integer.valueOf(id)).get();
        modifyedArticle.setPublished(ConverUtil.reversal(modifyedArticle.getPublished()));
        articleDao.saveAndFlush(modifyedArticle);
        return new Result(0, "操作成功", null);
    }

}
