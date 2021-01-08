package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author evan
 * 用户
 */
@Data
@TableName("users")
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
public class User extends BaseDomain {
    private static final long serialVersionUID = -5971060169689289963L;

    private String loginName;
    private String userName;
    private String password;
    private String phone;
    private String enable;
    private Long areaId;

}
