package com.kingja.blog.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * Description：TODO
 * Create Time：2018/4/3 11:21
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Data
public class UserDTO implements Serializable {
    private int id;
    private String username;
    private String jwt;

}
