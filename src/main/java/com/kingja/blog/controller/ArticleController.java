package com.kingja.blog.controller;

import com.kingja.blog.dao.ArticleDao;
import com.kingja.blog.dto.ArticleDTO;
import com.kingja.blog.entity.Article;
import com.kingja.blog.entity.ResultVO;
import com.kingja.blog.util.ResultVoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/api/visitor/article")
public class ArticleController {
    private Logger logger = LoggerFactory.getLogger(ArticleController.class);
    @Autowired
    private ArticleDao articleDao;
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

}
