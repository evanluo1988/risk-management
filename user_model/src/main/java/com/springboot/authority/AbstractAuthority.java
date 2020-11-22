package com.springboot.authority;

import com.springboot.exception.ServiceException;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractAuthority implements AuthorityService{

    @Override
    public boolean verifyPermission(Subject subject) throws ServiceException{
        List<Authority> accessAuthorityList = getAccessAuthorities(subject);
        List<Authority> authorityList = subject.getAuthorities();
        if(CollectionUtils.isEmpty(accessAuthorityList)){
            return true;
        }
        List accessPermissions = accessAuthorityList.stream().map(Authority::getPremission).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(authorityList)){
            //throw new ServiceException("没有权限！");
            return true;
        }
        List permissions = authorityList.stream().map(Authority::getPremission).collect(Collectors.toList());
        if(!permissions.contains(accessPermissions)){
            throw new ServiceException("没有权限！");
        }
        return true;
    }

    /**
     * 获取访问主体所需的权限列表
     * @param subject
     * @return
     */
    public abstract List<Authority> getAccessAuthorities(Subject subject);
}
