package com.mumu.core.utils;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2018/7/25.
 */
public class Locales {
    public static void main(String[] args) {
        // date为2013-09-19 14:22:30
        Date date = new Date(113, 8, 19, 14, 22, 30);

        // 创建“简体中文”的Locale
        Locale localeCN = Locale.CHINESE;
        // 创建“英文/美国”的Locale
        Locale localeUS = new Locale("en", "US");

        // 获取“简体中文”对应的date字符串
        String cn = DateFormat.getDateInstance(DateFormat.FULL, localeCN).format(date);
        // 获取“英文/美国”对应的date字符串
        String us = DateFormat.getDateInstance(DateFormat.MEDIUM, localeUS).format(date);

        System.out.printf("cn=%s\nus=%s\n", cn, us);
    }
}
