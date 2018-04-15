package com.kingja.blog.handler;

import com.kingja.blog.entity.ResultVO;
import com.kingja.blog.exception.BlogException;
import com.kingja.blog.exception.ResponseException;
import com.kingja.blog.util.ResultVoUtil;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Description：TODO
 * Create Time：2018/1/12 13:33
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@ControllerAdvice
public class SellExceptionHandler {
    @ExceptionHandler(value = BlogException.class)
    @ResponseBody
    public ResultVO handlerSellerException(BlogException e) {
        return ResultVoUtil.error(e.getCode(), e.getMessage());
    }

    /*直接回复状态值，比如403*/
    @ExceptionHandler(value = ResponseException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handlerResponseException() {}
}
