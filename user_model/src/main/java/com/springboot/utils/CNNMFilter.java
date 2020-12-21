package com.springboot.utils;

import java.util.HashMap;
import java.util.Map;

public class CNNMFilter {
    private static final Character[] CN_NUMERIC = { '一', '二', '三', '四', '五','六', '七', '八', '九',
            '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖',
            '1', '2', '3', '4', '5','6', '7', '8', '9',
            '十', '百', '千', '拾', '佰', '仟', '万', '亿', '○', 'Ｏ', '零' };
    private static Map<Character, Integer> cnNumeric = null;
    static {
        cnNumeric = new HashMap<Character, Integer>(40, 0.85f);
        for (int j = 0; j < 9; j++)
            cnNumeric.put(CN_NUMERIC[j], j + 1);
        for (int j = 9; j < 18; j++)
            cnNumeric.put(CN_NUMERIC[j], j - 8);
        for (int j = 18; j < 27; j++)
            cnNumeric.put(CN_NUMERIC[j], j - 17);
        cnNumeric.put('两', 2);
        cnNumeric.put('十', 10);
        cnNumeric.put('拾', 10);
        cnNumeric.put('百', 100);
        cnNumeric.put('佰', 100);
        cnNumeric.put('千', 1000);
        cnNumeric.put('仟', 1000);
        cnNumeric.put('万', 10000);
        cnNumeric.put('亿', 100000000);
        cnNumeric.put('0', 0);
    }

    public static int isCNNumeric(char c) {
        Integer i = cnNumeric.get(c);
        if (i == null)
            return -1;
        return i.intValue();
    }

    public static int cnNumericToArabic(String cnn, boolean flag) {
        cnn = cnn.trim();
        if (cnn.length() == 1)
            return isCNNumeric(cnn.charAt(0));
        if (flag)
            cnn = cnn.replace('佰', '百').replace('仟', '千').replace('拾', '十')
                    .replace('零', ' ');
        // System.out.println(cnn);
        int yi = -1, wan = -1, qian = -1, bai = -1, shi = -1;
        int val = 0;
        yi = cnn.lastIndexOf('亿');
        if (yi > -1) {
            val += cnNumericToArabic(cnn.substring(0, yi), false) * 100000000;
            if (yi < cnn.length() - 1)
                cnn = cnn.substring(yi + 1, cnn.length());
            else
                cnn = "";
            if (cnn.length() == 1) {
                int arbic = isCNNumeric(cnn.charAt(0));
                if (arbic <= 10)
                    val += arbic * 10000000;
                cnn = "";
            }
        }
        wan = cnn.lastIndexOf('万');
        if (wan > -1) {
            val += cnNumericToArabic(cnn.substring(0, wan), false) * 10000;
            if (wan < cnn.length() - 1)
                cnn = cnn.substring(wan + 1, cnn.length());
            else
                cnn = "";
            if (cnn.length() == 1) {
                int arbic = isCNNumeric(cnn.charAt(0));
                if (arbic <= 10)
                    val += arbic * 1000;
                cnn = "";
            }
        }
        qian = cnn.lastIndexOf('千');
        if (qian > -1) {
            val += cnNumericToArabic(cnn.substring(0, qian), false) * 1000;
            if (qian < cnn.length() - 1)
                cnn = cnn.substring(qian + 1, cnn.length());
            else
                cnn = "";
            if (cnn.length() == 1) {
                int arbic = isCNNumeric(cnn.charAt(0));
                if (arbic <= 10)
                    val += arbic * 100;
                cnn = "";
            }
        }
        bai = cnn.lastIndexOf('百');
        if (bai > -1) {
            val += cnNumericToArabic(cnn.substring(0, bai), false) * 100;
            if (bai < cnn.length() - 1)
                cnn = cnn.substring(bai + 1, cnn.length());
            else
                cnn = "";
            if (cnn.length() == 1) {
                int arbic = isCNNumeric(cnn.charAt(0));
                if (arbic <= 10)
                    val += arbic * 10;
                cnn = "";
            }
        }
        shi = cnn.lastIndexOf('十');
        if (shi > -1) {
            if (shi == 0)
                val += 1 * 10;
            else
                val += cnNumericToArabic(cnn.substring(0, shi), false) * 10;
            if (shi < cnn.length() - 1)
                cnn = cnn.substring(shi + 1, cnn.length());
            else
                cnn = "";
        }
        cnn = cnn.trim();
        for (int j = 0; j < cnn.length(); j++)
            val += isCNNumeric(cnn.charAt(j))
                    * Math.pow(10, cnn.length() - j - 1);
        return val;
    }

    public static int qCNNumericToArabic(String cnn) {
        int val = 0;
        cnn = cnn.trim();
        for (int j = 0; j < cnn.length(); j++)
            val += isCNNumeric(cnn.charAt(j))
                    * Math.pow(10, cnn.length() - j - 1);
        return val;
    }

    /**
     *
     * @description:获取金额
     * @Autor: ZZX
     * @param money
     * @return
     * @time:2017-7-31 上午11:26:19
     */
    public static String getAllNumber(String money){

        if(money == null || "".equals(money.trim())){
            return "0";
        }

        money = money.replaceAll("[，,]","");//去除千分位逗号
        String last = money.substring(money.length()-1, money.length()-0);
        //System.out.println("====="+money);
        if(last.equals("亿") || last.equals("万")){//以万和 亿结尾
            if(money.indexOf(".") != -1){//含小数点,除末位全是数字
                int weight = cnNumericToArabic(last,true);//带小数的万或亿单位转化为数字，作为权值
                String moneyWithTail = money.substring(0, money.length()-1);
                String b = (int)(Double.parseDouble(moneyWithTail)*weight)+"";
                return b;
            }else{
                String b = cnNumericToArabic(money,true)+"";
                return b;
            }
        }else if(money.indexOf(".") != -1){//含小数点,除末位全是数字

            String b = money+"";
            return b;
        }else{

            return cnNumericToArabic(money,true)+"";
        }



    }




    //测试
    public static void main(String[] args) {

//		String s = "九千伍佰七十三万";
//		String s = "33,22,1.00万";
//		String s = "000万";
        String s = "33,22,1.01万";
//		String s = "1112.33";
//		String s = "1千4百7十8万";
        System.out.println(getAllNumber(s));


    }
}
