package com.springboot.utils;

import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
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
     *
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
     *
     * @param moneyStr  金额
     * @param regCapCur 币种
     * @return
     */
    public static String getMoneyText(String moneyStr, String regCapCur) {
        if (moneyStr == null) {
            return "";
        }
        BigDecimal money = new BigDecimal(moneyStr);

        return money.divide(BigDecimal.valueOf(10000)).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "万元";
    }

    /**
     * 保留两位小数
     *
     * @param moneyStr
     * @return
     */
    public static String getMoney(String moneyStr) {
        if (moneyStr == null) {
            return "";
        }
        BigDecimal money = new BigDecimal(moneyStr);
        return money.stripTrailingZeros().toPlainString();
    }

    /**
     * 日期字符串
     *
     * @param date
     * @return
     */
    public static String getDataStr(String date) {
        if (date == null) {
            return "";
        }
        return date.replace("-", "/");
    }

    public static String getDataStr(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.toString().replace("-", "/");
    }

    /**
     * 百分比
     *
     * @param ratio
     */
    public static String getRatioStr(String ratio) {
        if (ratio == null) {
            return "";
        }
        BigDecimal bRatio = new BigDecimal(ratio);
        return bRatio.multiply(BigDecimal.valueOf(100)).stripTrailingZeros().toPlainString() + "%";
    }

    public static String getIntStr(String ratio) {
        if (ratio == null) {
            return "";
        }
        BigDecimal bRatio = new BigDecimal(ratio);
        return String.valueOf(bRatio.intValue());
    }


    /**
     * 如果字符串包含英文中文括号转换
     *
     * @param str 原串
     * @return
     */
    public static String brackets(String str) {
        String ZH_LEFT = "（";
        String ZH_RIGHT = "）";

        if (StringUtils.isEmpty(str)) {
            return str;
        }
        String result = str;
        if (str.contains(ZH_LEFT) || str.contains(ZH_RIGHT)) {
            String fistStepResult = str.replaceAll(ZH_LEFT, "(");
            result = fistStepResult.replaceAll(ZH_RIGHT, ")");
        }else if (str.contains("(") || str.contains(")")) {
            String fistStepResult = str.replaceAll("\\(", ZH_LEFT);
            result = fistStepResult.replaceAll("\\)", ZH_RIGHT);
        }

        return result;
    }

    public static void main(String[] args) {
        String entName = "测试(测试括号有限公司)有限公司";
        System.out.println(StrUtils.brackets(entName));

    }
}
