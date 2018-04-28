package com.kingja.blog.controller;

import com.kingja.blog.dao.UserDao;
import com.kingja.blog.dto.UserDTO;
import com.kingja.blog.entity.ResultVO;
import com.kingja.blog.entity.User;
import com.kingja.blog.enums.ResultEnum;
import com.kingja.blog.exception.BlogException;
import com.kingja.blog.util.JwtUtil;
import com.kingja.blog.util.ResultVoUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/4/6 19:18
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@RestController
@RequestMapping(value = "/api")
@Slf4j
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserDao userDao;

    @CrossOrigin
    @PostMapping("/login")
    public ResultVO login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String
            password, HttpServletResponse response, HttpServletRequest request) {
        logger.error("username:"+username+"password:"+password);
        String token = request.getHeader("token");
        logger.error("token:"+token);
        List<User> users = userDao.findUserByUsernameAndPassword(username, password);
        if (users.size() > 0) {
            User user = users.get(0);
            String jwt = JwtUtil.createJWT(user.getId() + "", user.getUsername(), 1000 * 60 * 60 * 24
                    * 30);
            Cookie cookie = new Cookie("token", jwt);
            cookie.setPath("/api");
            cookie.setMaxAge(60*10000); //设置cookie的过期时间是10s
            response.addCookie(cookie);

            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setJwt(jwt);

            return ResultVoUtil.success(userDTO);
        }
         throw new BlogException(ResultEnum.LOGIN_ERROR.getCode(), ResultEnum.LOGIN_ERROR.getMsg());

    }
}
