package com.kingja.blog.dao;

import com.kingja.blog.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import javax.transaction.Transactional;

/**
 * Description：TODO
 * Create Time：2018/4/3 11:25
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Transactional
public interface UserDao extends JpaRepository<User, Integer> {
    List<User> findUserByUsernameAndPassword(String username, String password);
}
