package com.springboot.authority;

import com.springboot.exception.ServiceException;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author evan
 */
public abstract class AbstractAuthority implements AuthorityService{

    @Override
    public boolean verifyPermission(Subject subject) throws ServiceException{
        List<Authority> accessAuthorityList = getAccessAuthorities(subject);
        if(CollectionUtils.isEmpty(accessAuthorityList)){
            return true;
        }
        List<Authority> authorityList = getAuthorities(subject);
        if(CollectionUtils.isEmpty(authorityList)){
            throw new ServiceException("没有权限！");
        }
        List accessPermissions = accessAuthorityList.stream().map(Authority::getPremission).collect(Collectors.toList());
        List permissions = authorityList.stream().map(Authority::getPremission).collect(Collectors.toList());
        if(verify(permissions, accessPermissions)){
            throw new ServiceException("没有权限！");
        }
        return true;
    }

    /**
     * 此方法有需要可覆盖，目前当前所有权限包含所有的访问所需权限才可以访问
     * @param permissions 当前所有权限
     * @param accessPermissions 访问所需权限
     * @return
     */
    public boolean verify(List<Object> permissions, List<Object> accessPermissions){
        return permissions.contains(accessPermissions);
    }

    /**
     * 获取访问主体所需的权限列表
     * @param subject
     * @return
     */
    public abstract List<Authority> getAccessAuthorities(Subject subject);

    /**
     * 获取当前已有的权限列表
     * @param subject
     * @return
     */
    public abstract List<Authority> getAuthorities(Subject subject);
}
