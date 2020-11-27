package com.springboot.utils;

import com.springboot.enums.RoleEnum;
import com.springboot.model.RolePerm;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * role相关业务工具类
 *
 * @Author 刘宏飞
 * @Date 2020/11/27 9:34
 * @Version 1.0
 */
public class RoleUtils {
    /**
     * 查询最高权限的role
     * @param rolePerms
     * @return
     */
    public static RolePerm getHighestLevelRole(Collection<RolePerm> rolePerms) {
        if (CollectionUtils.isEmpty(rolePerms)) {
            return null;
        }

        RolePerm highestLevelRole = null;
        for (RolePerm rolePerm : rolePerms) {
            //如果最高权限是null或者最高权限级别小于当前权限级别
            if (Objects.isNull(highestLevelRole)
                    || RoleEnum.nameOf(rolePerm.getRoleName()).ordinal()
                    < RoleEnum.nameOf(highestLevelRole.getRoleName()).ordinal()) {
                highestLevelRole = rolePerm;
            }
        }

        return highestLevelRole;
    }
}
