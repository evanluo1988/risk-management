package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 刘宏飞
 * @since 2020-11-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("inform_persons")
public class InformPerson extends BaseDomain {

    private static final long serialVersionUID = 1L;

    /**
     * 举报人姓名
     */
    private String personName;

    /**
     * 举报人身份证号
     */
    private String identityCard;

    /**
     * 联系方式
     */
    private String contactWay;

    /**
     * 单位/住址
     */
    private String address;
}
