package com.jiekai.wzglld.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LaoWu on 2017/12/6.
 * 字符串工具类
 */

public class StringUtils {
    /**
     * 判断字符串是否为空
     * @param msg
     * @return
     */
    public static boolean isEmpty(String msg) {
        if (msg == null || msg.length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 去除字符串中的空格，回车，制表符，换行符
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}
