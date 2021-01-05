package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author 刘宏飞
 * @Date 2020/12/16 14:31
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName(value = "std_ent_person_list")
public class StdEntPerson extends BaseDomain {

    private String reqId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 职务
     */
    private String position;
}
