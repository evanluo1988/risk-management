package com.springboot.authority;

import com.springboot.exception.ServiceException;

/**
 * @author evan
 * 权限服务
 */
public interface AuthorityService {
    /**
     * 验证主体权限
     * @param subject
     * @return
     */
    boolean verifyPermission(Subject subject) throws ServiceException;
}
