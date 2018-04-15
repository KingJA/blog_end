package com.kingja.blog.entity;

import lombok.Data;

/**
 * Description：TODO
 * Create Time：2016/11/16 14:00
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Data
public class ResultVO<T> {
    private int resultCode;
    private String resultText;
    private T resultData;
}
