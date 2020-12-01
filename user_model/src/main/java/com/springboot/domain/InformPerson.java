package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import BaseDomain;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("inform_person")
public class InformPerson extends BaseDomain<InformPerson> {

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

    /**
     * 举报来源
     */
    private String source;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 更新者
     */
    private String updateBy;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
