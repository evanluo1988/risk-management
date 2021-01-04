package com.springboot.utils;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class CalculatUtil {
    private static final double MINUS_INF = new Double(-99999999); // 负无穷
    private static final double PLUS_INF = 99999999; // 正无穷
    private static final double ZERO = 0; // 零

    /**
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @param scale    保留小数位数
     * @return 比例 add by luohuan 2018年12月6日20:19:20
     */
    public static Double BigDecimalDivide(BigDecimal dividend, BigDecimal divisor, int scale) {
        return dividend.divide(divisor, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 比例特殊处理
     *
     * @param dividend 被除数(分子)
     * @param divisor  除数（分母）
     * @param scale    保留小数位数
     * @return 比例 add by luohuan 2018年12月6日20:19:20 if 分母 is null or 分子 is null then
     *         null else if 分母 =0 & 分子<0 then -inf else if 分母 =0 & 分子>0 then inf
     *         else if 分母=0 & 分子=0 then null else 正常计算
     *
     */
    public static Double BigDecimalDivideForProportion(BigDecimal dividend, BigDecimal divisor, int scale) {
        if (divisor == null || dividend == null) {
            return null;
        } else if (BigDecimal.ZERO.compareTo(divisor) == 0 && BigDecimal.ZERO.compareTo(dividend) == 1) {
            return MINUS_INF;
        } else if (BigDecimal.ZERO.compareTo(divisor) == 0 && BigDecimal.ZERO.compareTo(dividend) == -1) {
            return PLUS_INF;
        } else if (BigDecimal.ZERO.compareTo(divisor) == 0 && BigDecimal.ZERO.compareTo(dividend) == 0) {
            return null;
        } else {
            return dividend.divide(divisor, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
    }

    /**
     * 增长率处理
     *
     * @param dividend 被除数(分子)order2
     * @param divisor  除数（分母）order1
     * @param scale    保留小数位数
     * @return 比例 add by luohuan 2018年12月6日20:19:20 定义：前一期为order1，后一期为order2 if
     *         order1 is null or order2 is null then null else if order1 = 0 & order
     *         2 = 0 then 0 else if order1 = 0 & order2 <0 then -inf else if order1
     *         = 0 & order2 >0 then inf else if order1<0 then
     *         增长率=(order2-order1)/ABS(order1) else 正常计算
     *
     */
    public static Double BigDecimalDivideForGrowth(BigDecimal dividend, BigDecimal divisor, int scale) {
        if (divisor == null || dividend == null) {
            return null;
        } else if (BigDecimal.ZERO.compareTo(divisor) == 0 && BigDecimal.ZERO.compareTo(dividend) == 0) {
            return ZERO;
        } else if (BigDecimal.ZERO.compareTo(divisor) == 0 && BigDecimal.ZERO.compareTo(dividend) == 1) {
            return MINUS_INF;
        } else if (BigDecimal.ZERO.compareTo(divisor) == 0 && BigDecimal.ZERO.compareTo(dividend) == -1) {
            return PLUS_INF;
        } else if (BigDecimal.ZERO.compareTo(divisor) == 1) {
            return dividend.subtract(divisor).divide(divisor.abs(), scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        } else {
            return dividend.divide(divisor, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
    }

    // List<Map<String, Object>>类型去除重复项
    public static List<Map<String, Object>> removeDuplicate(List<Map<String, Object>> list)
            throws SQLException, IOException {

        for (Map<String, Object> map : list) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof Clob) {
                    entry.setValue(ClobToString((Clob) entry.getValue()));
                }
            }
        }

        HashSet<Map<String, Object>> set = new HashSet<Map<String, Object>>(list);
        list.clear();
        list.addAll(set);
        return list;
    }

    // 将字Clob转成String类型
    public static String ClobToString(Clob clob) throws SQLException, IOException {

        String reString = "";
        Reader is = clob.getCharacterStream();// 得到流
        BufferedReader br = new BufferedReader(is);
        String s = br.readLine();
        StringBuffer sb = new StringBuffer();
        while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
            sb.append(s);
            s = br.readLine();
        }
        reString = sb.toString();
        return reString;
    }

    /*
     * public static void main(String[] args) { BigDecimal dividend = new
     * BigDecimal(-123); BigDecimal divisor = new BigDecimal(0);
     * System.out.println(BigDecimalDivideForGrowth(dividend, divisor, 4) < 0); }
     */

    /**
     * 判断是否含有非空格字符
     *
     * @param str
     * @return
     */
    public static boolean hasChar(Object str) {
        if(str==null){
            return false;
        }
        return !StringUtils.isEmpty(StringUtils.trim(String.valueOf(str)));
    }

    /**
     * 判断是否含有非空格字符
     *
     * @param str
     * @return
     */
    public static boolean hasChar(String[] str) {
        for (String s : str) {
            if (StringUtils.isEmpty(StringUtils.trim(s))) {
                return false;
            }
        }
        return true;
    }

    //
    public static boolean equals(String[] arr, String s) {
        boolean flag = Boolean.FALSE;
        if (arr != null && arr.length > 0 && s != null) {
            for (String s1 : arr) {
                if (s.equals(s1)) {
                    flag = Boolean.TRUE;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     *
     * @description: sor是否包含test字符串[可判断null值]
     */
    public static boolean inStr(String test, String sor) {
        if (sor == null || test == null) {
            return false;
        } else {
            return sor.contains(test);
        }
    }

    /**
     * @description:查看String是否在数组对象中,[复写的contains，针对数组]
     */
    public static boolean contains(String[] arr, String s) {
        boolean flag = Boolean.FALSE;
        if (arr != null && arr.length > 0 && s != null) {
            for (String s1 : arr) {
                if (s.contains(s1)) {
                    flag = Boolean.TRUE;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * @param dateStr-casedate
     * @description:时间相减得到天数
     */
    public static Long getDaySub(Object casedate, String dateStr) throws Exception {
        if (!hasChar(dateStr)) {
            return null;
        }

        long day;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date beginDate;
        try {
            dateStr = dateStr.replaceAll("[年月/]", "-").replaceAll("日", "");
            beginDate = format.parse(dateStr);
            day = (beginDate.getTime() - ((Date) casedate).getTime()) / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            return null;// 做空处理
        }
        return day;
    }

    /**
     * 字符串判等【两者皆不为空，且相等】
     */
    public static boolean myEquals(String a, String b) {
        if (a != null && b != null && a.equals(b)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 多条结果值判断最终结果
     *
     * @param str
     * @return
     */
    public static String getFinalResult(StringBuffer str) {
        if (str.indexOf("1") != -1) {
            return "1";
        }
        if (str.indexOf("2") != -1) {
            return "2";
        }
        return "0";
    }

    /**
     * 判断两个字符串是否是包含关系
     * @param str1
     * @param str2
     * @return boolean
     */
    public static boolean twoStrContains(String str1, String str2) {
        boolean flag = Boolean.FALSE;
        if (!hasChar(str1)|| !hasChar(str2)) {
            return flag;
        }
        if (str1.contains(str2) || str2.contains(str1)) {
            return Boolean.TRUE;
        }
        return flag;
    }

    /**
     *四舍五入到小数点后4位
     * @param d
     * @return
     */
    public static Double DoubleConversion(Double d){
        BigDecimal b = new BigDecimal(d);
        d = b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        return d;
    }
}
