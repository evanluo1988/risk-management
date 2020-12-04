package com.springboot.vo;

import com.springboot.domain.Inform;
import com.springboot.domain.InformCheck;
import lombok.Data;

/**
 * @Author 刘宏飞
 * @Date 2020/12/4 14:53
 * @Version 1.0
 */
@Data
public class InformViewVo {
    private Inform inform;
    private InformCheck informCheck;
}
