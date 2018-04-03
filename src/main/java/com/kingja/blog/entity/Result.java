package com.kingja.blog.entity;

/**
 * Description：TODO
 * Create Time：2016/11/16 14:00
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Result<T> {
    private int resultCode;
    private String resultText;
    private T resultData;

    public Result() {

    }  public Result(int resultCode, String resultText, T resultData) {
        this.resultCode = resultCode;
        this.resultText = resultText;
        this.resultData = resultData;
    }

    public String getResultText() {
        return resultText;
    }

    public Result setResultText(String resultText) {
        this.resultText = resultText;
        return this;
    }

    public T getResultData() {
        return resultData;
    }

    public Result setResultData(T resultData) {
        this.resultData = resultData;
        return this;
    }

    public int getResultCode() {

        return resultCode;
    }

    public Result setResultCode(int resultCode) {
        this.resultCode = resultCode;
        return this;
    }
}
