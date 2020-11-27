package com.springboot.utils;

import com.springboot.domain.User;
import com.springboot.model.RolePerm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Collection;
import java.util.Date;

/**
 * 用户认证信息上下文
 *
 * @Author 刘宏飞
 * @Date 2020/11/24 15:52
 * @Version 1.0
 */
public class UserAuthInfoContext {
    /**
     * 上下文
     */
    private static final ThreadLocal<UserAuthInfoHolder> context = new ThreadLocal<>();

    /**
     * 内部结构，即数据持有者
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class UserAuthInfoHolder {
        private Long id;
        private String userName;
        private Long areaId;
        private Date createTime;
        private Date updateTime;
        private String createBy;
        private String updateBy;
        private String enable;

        private Collection<RolePerm> rolePerms;
    }

    /**
     * 设置数据
     *
     * @param user     用户基本信息
     * @param rolePerms 用户角色权限信息
     */
    public static void set(User user, Collection<RolePerm> rolePerms) {
        UserAuthInfoHolder userAuthInfoHolder = new UserAuthInfoHolder();
        //copy user base info
        BeanUtils.copyProperties(user, userAuthInfoHolder);
        //link rolePerm
        userAuthInfoHolder.setRolePerms(rolePerms);
        clear().set(userAuthInfoHolder);
    }

    public static UserAuthInfoHolder getUser(){
        return context.get();
    }

    /**
     * 获取用户ID
     *
     * @return 用户ID
     */
    public static Long getUserId() {
        return context.get().getId();
    }

    /**
     * 获取用户名
     *
     * @return 用户名
     */
    public static String getUserName() {
        return context.get().getUserName();
    }

    /**
     * 获取用户管辖区域ID
     *
     * @return 管辖区域ID
     */
    public static Long getAreaId() {
        return context.get().getAreaId();
    }

    /**
     * 获取用户状态
     * @return
     */
    public static String getEnable(){
        return context.get().getEnable();
    }

    /**
     * 获取用户创建时间
     *
     * @return 创建时间
     */
    public static Date getCreateTime() {
        return context.get().getCreateTime();
    }

    /**
     * 获取用户更新时间
     *
     * @return 更新时间
     */
    public static Date getUpdateTime() {
        return context.get().getUpdateTime();
    }

    /**
     * 获取用户创建者
     *
     * @return 创建者
     */
    public static String getCreateBy() {
        return context.get().getCreateBy();
    }

    /**
     * 获取用户更新者
     *
     * @return 更新者
     */
    public static String getUpdateBy() {
        return context.get().getUpdateBy();
    }

    /**
     * 获取用户角色权限
     *
     * @return 角色权限
     */
    public static Collection<RolePerm> getRolePerms() {
        return context.get().getRolePerms();
    }

    /**
     * 清除上下文
     *
     * @return 上下文
     */
    public static ThreadLocal<UserAuthInfoHolder> clear() {
        context.remove();
        return context;
    }
}
