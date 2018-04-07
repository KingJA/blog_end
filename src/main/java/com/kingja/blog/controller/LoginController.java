package com.kingja.blog.controller;

import com.kingja.blog.dao.UserDao;
import com.kingja.blog.entity.Result;
import com.kingja.blog.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * Description:TODO
 * Create Time:2018/4/6 19:18
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@RestController
@RequestMapping(value = "/api/admin")
@Slf4j
public class LoginController {

    @Autowired
    private UserDao userDao;

    @CrossOrigin
    @PostMapping("/login")
    public Result login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String
            password) {
        log.debug("username:" + username);
        log.debug("password:" + password);
        System.out.println("username:" + username);
        System.out.println("password:" + password);
        List<User> users = userDao.findUserByUsernameAndPassword(username, password);
        if (users.size() > 0) {
            return new Result(0, "登录成功", users.get(0));
        } else {
            return new Result(1, "登录失败", null);
        }

    }
}
