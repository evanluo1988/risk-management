package com.springboot.model;

import lombok.Data;

/**
 * 字符串中是否包含中文和英文的括号  ()  （）
 * @author lhf
 * @version 1.0
 * @date 2021/1/25 14:18
 */
@Data
public class BracketsModel {
    /**
     * 替换成英文括号
     */
    private String en;
    /**
     * 替换成中文括号
     */
    private String zh;
    /**
     * 是否包含括号
     */
    private boolean has;
}
