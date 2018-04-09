package com.kingja.blog.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description：TODO
 * Create Time：2018/4/9 16:06
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class DateUtil {
    public static String getStringDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }
}
