package com.kingja.blog.controller;

import com.kingja.blog.dao.UserDao;
import com.kingja.blog.entity.Result;
import com.kingja.blog.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
            password,HttpServletResponse response) {
        log.debug("username:" + username);
        log.debug("password:" + password);
        System.out.println("username:" + username);
        System.out.println("password:" + password);
        String origin = response.getHeader("Origin");
        if(origin == null) {
            origin = response.getHeader("Referer");
        }
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");            // 允许指定域访问跨域资源
        response.setHeader("Access-Control-Allow-Credentials", "true");       // 允许客户端携带跨域cookie，此时origin值不能为“*”，只能为指定单一域名
        List<User> users = userDao.findUserByUsernameAndPassword(username, password);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (users.size() > 0) {
            Cookie cookie = new Cookie("token", users.get(0).getUsername());
            cookie.setMaxAge(60*10); //设置cookie的过期时间是10s
            response.addCookie(cookie);
            return new Result(0, "登录成功", users.get(0));
        } else {
            return new Result(1, "登录失败", null);
        }

    }
}
