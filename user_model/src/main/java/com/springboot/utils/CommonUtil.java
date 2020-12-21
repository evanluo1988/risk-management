package com.springboot.utils;

import org.apache.commons.lang.StringUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CommonUtil {
    public static final String DateFormat = "yyyy/MM/dd";//日期格式统一样式

    /**
     * 判断是否含有非空格字符
     * @param str
     * @return
     */
    public static boolean hasChar(String str){
        return StringUtils.isNotBlank(str);
    }

    /**
     * 判断是否含有非空格字符
     * @param str
     * @return
     */
    public static boolean hasChar(String[] str){
        for (String s : str) {
            if(StringUtils.isEmpty(StringUtils.trim(s))){
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @description:Map为空或null
     * @Autor: ZZX
     * @param map
     * @return
     * @time:2017-8-11 下午3:51:37
     */
    @SuppressWarnings("rawtypes")
    public static boolean mapIsEmptyOrNull(Map map){
        if(map == null || map.isEmpty()){
            return true;
        }else{
            return false;
        }
    }





    /**
     *
     * @description:字符串首位大写
     * @Autor: ZZX
     * @param s
     * @return
     * @time:2017-8-15 下午5:39:47
     */
    public static String upperCaseFirstCharat(String s){
        if(s != null && s.length() > 0){
            char[] cs=s.toCharArray();
            if(cs[0] >= 'a' && cs[0] <= 'z'){
                cs[0]-=32;
            }
            return String.valueOf(cs);
        }else{
            return "";
        }
    }





    /**
     *
     * @description:查看对象是否在list中
     * @Autor: ZZX
     * @param arr
     * @param o
     * @return
     * @time:2017-7-26 下午3:41:40
     */
    public static <T> boolean contains(List<T> arr, Object o){
        boolean flag = Boolean.FALSE;
        if(arr != null && arr.size() > 0 && o != null){
            for (Object object : arr) {
                if(object.equals(o)){
                    flag = Boolean.TRUE;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     *
     * @description:查看String是否在数组对象中,[复写的contains，针对数组]
     * @Autor: ZZX
     * @param arr
     * @param s
     * @return
     * @time:2017-7-26 下午3:44:07
     */
    public static boolean contains(String[] arr,String s ){
        boolean flag = Boolean.FALSE;
        if(arr != null && arr.length > 0 && s != null){
            for (String s1 : arr) {
                if(s.contains(s1)){
                    flag = Boolean.TRUE;
                    break;
                }
            }
        }
        return flag;
    }


    /**
     *
     * @description:查看String是否在数组对象中[复写的equals，针对数组]
     * @Autor: ZZX
     * @param arr
     * @param s
     * @return
     * @time:2017-7-26 下午3:44:07
     */
    public static boolean equals(String[] arr,String s ){
        boolean flag = Boolean.FALSE;
        if(arr != null && arr.length > 0 && s != null){
            for (String s1 : arr) {
                if(s.equals(s1)){
                    flag = Boolean.TRUE;
                    break;
                }
            }
        }
        return flag;
    }


    /**
     * 字符串判等【两者皆不为空，且相等】
     */
    public static boolean myEquals(String a,String b){
        if(a != null && b != null && a.equals(b)){
            return true;
        }else{
            return false;
        }
    }


    /**
     *
     * @description: sor是否包含test字符串[可判断null值]
     * @Autor: ZZX
     * @param test 是否在sor中
     * @param
     * @return
     * @time:2017-8-8 下午1:59:04
     */
    public static boolean inStr(String test,String sor){
        if(sor == null){
            return false;
        }else{
            if(test == null){
                return false;
            }else{
                return sor.contains(test);
            }
        }

    }



    /**
     *
     * @description:数组join
     * @Autor: ZZX
     * @param a
     * @param i
     * @return
     * @time:2017-7-31 下午6:19:44
     */
    public static String join(int[] a,String i){
        StringBuffer s = new StringBuffer();
        for (int z : a) {
            s.append(z).append(i);
        }
        return s.toString();
    }

    /**
     *
     * @description:数组join
     * @Autor: ZZX
     * @param a
     * @param i
     * @return
     * @time:2017-7-31 下午6:19:44
     */
    public static String join(String[] a,String i){
        StringBuffer s = new StringBuffer();
        for (String z : a) {
            s.append(z).append(i);
        }
        return s.toString();
    }


    /**
     *
     * @description:时间相减得到天数
     * @Autor: ZZX
     * @param beginDateStr
     * @param endDateStr
     * @return
     * @throws Exception
     * @throws ParseException
     * @time:2017-8-1 上午2:05:08
     */
    public static Long getDaySub(String beginDateStr,Date endDate) throws Exception
    {
        if(!hasChar(beginDateStr)){
            return null;
        }

        long day;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date beginDate;
        try {
            beginDateStr = beginDateStr.replaceAll("[年月/]", "-").replaceAll("日", "");
            beginDate = format.parse(beginDateStr);
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
        } catch (ParseException e) {
            System.out.println(e.getMessage()+"【需要转换的时间格式不正确，做空处理】");
            return null;//做空处理
        }
        return day;
    }


    /**
     *
     * @description:天数差值
     * @Autor: ZZX
     * @time:2017-8-9 上午12:10:01
     */
    public static Long getDaySub(Date begin, Date end){
        return (end.getTime()-begin.getTime())/(24*60*60*1000);
    }

    /**
     *
     * @Dscription:double 格式化2位小数
     * @Autor: ZZX
     * @param d
     * @return
     * @time:2017-8-4 下午6:54:46
     */
    public static String getDoubleString(double d){
        DecimalFormat df = new java.text.DecimalFormat("#.00");//("#,##0.00")
        return df.format(d);
    }


    /**
     *
     * @description:格式化 安硕时间字段
     * @Autor: LiuCong
     * @param text
     * @param returnformat
     * @return
     * @time:2017-8-15 下午12:48:59
     */
    public static String parseInit(String text,String returnformat)  {
        if(text == null){
            return "";
        }
        String[] formats = {"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd","yyyy/MM/dd HH:mm:ss",
                "yyyy.MM.dd","yyyy年MM月dd日","yyyy,MM,dd","yyyyMMdd","yyyy/MM/dd"
        };
        Date date = null;
        if(text.indexOf(":") >= 0 && text.indexOf(" ") == -1){
            return "";
        }
        String isJy = "";
        for(String format : formats){
            SimpleDateFormat format1 = new SimpleDateFormat(format);
            try {
                date = format1.parse(text);
                if(date.getTime() > 0){
                    isJy = format1.format(date);
                    isJy = isJy.replaceAll("0", "");
                    text = text.replaceAll("0", "");
                    if(text.equals(isJy)){
                        format1 = new SimpleDateFormat(returnformat);
                        return  format1.format(date);
                    }
                }
            } catch (Exception e) {
            }
        }
        return "";
    }




    public static void main(String[] args) throws Exception {

		/*gett("asdasd");
		try {
			EDSUtil.ExceptionWithMessage(null, null, null);
		} catch (Exception e) {
//			e.printStackTrace();
		}*/
//		System.out.println(getDaySub("201",new Date()) > 0);

//    	String s[] ={"aa","ab","c","d"};
//    	String a = "aaa";
//    	String c = "c";
//    	System.out.println(contains(s, a));
//    	System.out.println(equals(s, c));

//    	System.out.println(parseInit("2011","yyyy-MM-dd"));

    /*	char s = 'a';//97
    	char ss = 'z';//122
    	char s1 = 'A';//65
    	char s2 = 'Z';//90
    	System.out.println(s + 0);
    	System.out.println(ss + 0);
    	System.out.println(s1 + 0);
    	System.out.println(s2 + 0);*/

//    	System.out.println(upperCaseFirstCharat("Ss"));

//    	System.out.println(rightTime("20170307"));

//    	String sor[] = {"bbb","aaa","abc"};
//    	String test = "ab";
//    	System.out.println(inStr(test, sor));
    }



    /**
     *
     * @description:时间格式是否正确
     * @Autor: ZZX
     * @param caseCreateTime
     * @return
     * @time:2017-10-21 下午1:57:10
     */
    public static boolean rightTime(String caseCreateTime) {

        String date = parseInit(caseCreateTime, "yyyyMMdd");

        if(date != null && !"".equals(date)){

            return true;

        }
        return false;
    }
}
