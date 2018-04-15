package com.kingja.blog.exception;


import com.kingja.blog.enums.ResultEnum;

import lombok.Getter;

/**
 * Description：TODO
 * Create Time：2018/1/10 15:26
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Getter
public class BlogException extends RuntimeException {
    private Integer code;

    public BlogException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public BlogException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }
}
