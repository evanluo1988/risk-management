package com.springboot.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

/**
 * @Author 刘宏飞
 * @Date 2020/12/4 10:31
 * @Version 1.0
 */
public class StrUtils {
    /**
     * 获取随机数
     *
     * @param length 随机数的长度
     * @return 随机数
     */
    public static String randomNum(int length) {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    /**
     * 获取UUID
     *
     * @return
     */
    public static String uuid() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }


    /**
     * 随机字符串
     * @param length
     * @return
     */
    public static String randomStr(int length) {
        String linkNo = "";
        // 用字符数组的方式随机
        String model = "0123456789abcdefghigklmnopqstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] m = model.toCharArray();
        for (int j = 0; j < length; j++) {
            char c = m[(int) (Math.random() * 36)];
            if (linkNo.contains(String.valueOf(c))) {
                j--;
                continue;
            }
            linkNo = linkNo + c;
        }
        return linkNo;
    }
}
