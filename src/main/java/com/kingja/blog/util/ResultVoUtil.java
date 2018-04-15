package com.kingja.blog.util;


import com.kingja.blog.entity.ResultVO;

/**
 * Description：TODO
 * Create Time：2018/1/10 10:04
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ResultVoUtil {

    public static ResultVO success(Object data) {
        ResultVO<Object> resultVO = new ResultVO<>();
        resultVO.setResultCode(0);
        resultVO.setResultText("成功");
        resultVO.setResultData(data);
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO<Object> resultVO = new ResultVO<>();
        resultVO.setResultCode(code);
        resultVO.setResultText(msg);
        return resultVO;
    }

}
