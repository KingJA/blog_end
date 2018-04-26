package com.kingja.blog.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Description：TODO
 * Create Time：2016/11/16 14:00
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Data
public class ResultVO<T> implements Serializable {
    private static final long serialVersionUID = -1058469400042601345L;
    private int resultCode;
    private String resultText;
    private T resultData;
}
