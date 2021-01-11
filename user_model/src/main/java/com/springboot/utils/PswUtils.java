package com.springboot.utils;

import java.util.regex.Pattern;

public class PswUtils {
    /**
     * 密码是否符合规则
     * 新密码必须为8-16为数字、字母或下划线
     * @param password   密码铭文
     * @return
     */
    public static boolean isConformRule(String password) {
        String pattern = "^\\w{8,16}$";
        return Pattern.matches(pattern, password);
    }

}
