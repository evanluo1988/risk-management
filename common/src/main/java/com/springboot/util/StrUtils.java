package com.springboot.util;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    /**
     * 获取钱
     * @param moneyStr 金额
     * @param regCapCur 币种
     * @return
     */
    public static String getMoneyText(String moneyStr, String regCapCur) {
        if(moneyStr == null) {
            return null;
        }
        BigDecimal money = new BigDecimal(moneyStr);

        return money.divide(BigDecimal.valueOf(10000)).stripTrailingZeros().toPlainString()+"万元";
    }

    /**
     * 保留两位小数
     * @param moneyStr
     * @return
     */
    public static String getMoney(String moneyStr) {
        if(moneyStr == null) {
            return null;
        }
        BigDecimal money = new BigDecimal(moneyStr);
        return money.stripTrailingZeros().toPlainString();
    }

    /**
     * 日期字符串
     * @param date
     * @return
     */
    public static String getDataStr(String date) {
        if(date == null) {
            return null;
        }
        return date.replace("-", "/");
    }

    public static String getDataStr(LocalDate date) {
        if(date == null) {
            return null;
        }
        return date.toString().replace("-", "/");
    }

    /**
     * 百分比
     * @param ratio
     */
    public static String getRatioStr(String ratio) {
        if(ratio == null){
            return null;
        }
        BigDecimal bRatio = new BigDecimal(ratio);
        return bRatio.multiply(BigDecimal.valueOf(100)).stripTrailingZeros().toPlainString()+"%";
    }

    public static void main(String[] args) {
        System.out.println(StrUtils.getDataStr(LocalDate.now()));
    }

    public static String getIntStr(String ratio) {
        if(ratio == null){
            return null;
        }
        BigDecimal bRatio = new BigDecimal(ratio);
        return String.valueOf(bRatio.intValue());
    }
}
