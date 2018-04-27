package com.kingja.blog.controller;

import com.kingja.blog.dao.ArticleDao;
import com.kingja.blog.dao.CatalogDao;
import com.kingja.blog.dto.ArticleDTO;
import com.kingja.blog.entity.Article;
import com.kingja.blog.entity.Catalog;
import com.kingja.blog.entity.ResultVO;
import com.kingja.blog.util.ConverUtil;
import com.kingja.blog.util.ResultVoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * Description：TODO
 * Create Time：2018/4/3 11:16
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@RestController
@RequestMapping(value = "/api/article")
public class ArticleController {
    private Logger logger = LoggerFactory.getLogger(ArticleController.class);
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private CatalogDao catalogDao;

    @CrossOrigin
    @PostMapping(value = "/add")
    @CacheEvict(cacheNames = "articles", key = "888")
    public ResultVO addArticle(Article article) {
        logger.error(article.toString());
        articleDao.save(article);
        return  ResultVoUtil.success(article);

    }


    @CrossOrigin
    @PostMapping(value = "/all")
//    @Cacheable(cacheNames = "articles", key = "888")
    public ResultVO getArticles() {
        if (new Random().nextInt(2) == 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return  ResultVoUtil.error(1,"获取文章失败");
        }else{
            logger.error("获取全部文章");
            List<ArticleDTO> articles = articleDao.findArticleItem();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return  ResultVoUtil.success(articles);
        }

    }

    @CrossOrigin
    @GetMapping(value = "/get")
    public ResultVO getArticleById(@RequestParam("id") String id) {
        Article article = articleDao.findById(Integer.valueOf(id)).get();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResultVoUtil.success(article);
    }

    @CrossOrigin
    @PostMapping(value = "/modify")
    @CacheEvict(cacheNames = "articles", key = "888")
    public ResultVO modifyArticle(Article article) {
        Article exitArticle = articleDao.findById(article.getId()).get();
        exitArticle.setTitle(article.getTitle());
        exitArticle.setContent(article.getContent());
        articleDao.saveAndFlush(exitArticle);
         return ResultVoUtil.success();
    }

    @CrossOrigin
    @PostMapping(value = "/delete")
    public ResultVO deleteArticle(@RequestParam("id") String id) {
        articleDao.deleteById(Integer.valueOf(id));
        return ResultVoUtil.success();
    }

    @CrossOrigin
    @PostMapping(value = "/query")
    public ResultVO query(@RequestParam("type") String type, @RequestParam("keyword") String keyword) {
        keyword = "%" + keyword + "%";
        List<Article> results;
        if ("title".equals(type)) {
            results = articleDao.findByTitleLike(keyword);
        } else {
            results = articleDao.findByContentLike(keyword);
        }
        return ResultVoUtil.success(results);
    }

    @CrossOrigin
    @PostMapping(value = "/published")
    public ResultVO setPublishedStatus(@RequestParam("id") String id) {
        Article modifyedArticle = articleDao.findById(Integer.valueOf(id)).get();
        modifyedArticle.setPublished(ConverUtil.reversal(modifyedArticle.getPublished()));
        articleDao.saveAndFlush(modifyedArticle);
        return ResultVoUtil.success();
    }

    @CrossOrigin
    @GetMapping(value = "/catalog")
    public ResultVO getCatalogs() {
        List<Catalog> result = catalogDao.findAll();
        return ResultVoUtil.success(result);
    }

    @CrossOrigin
    @PostMapping(value = "/articlesBycatalogId")
    public ResultVO getArticlesByCagalogId(@RequestParam("catalogid") String catalogid) {
        List<Article> articles = articleDao.findByCatalogid(Integer.valueOf(catalogid) );
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  ResultVoUtil.success(articles);
    }
}
