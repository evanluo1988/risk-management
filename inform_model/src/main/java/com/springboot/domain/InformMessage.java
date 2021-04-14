package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author lhf
 * @date 2021/4/1 4:18 下午
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("inform_messages")
public class InformMessage extends BaseDomain{
    /**
     * 被回复消息ID
     */
    private Long reId;
    /**
     * 发表用户ID
     */
    private Long userId;
    /**
     * 发表内容
     */
    private String content;
    /**
     * 举报ID
     */
    private Long informId;

}
