package com.springboot.authority;

import com.springboot.exception.ServiceException;

/**
 * @author evan
 * 权限管理器
 */
public class AuthorityManagement {

     private AuthorityService authorityService;

     public AuthorityManagement(AuthorityService authorityService){
         this.authorityService = authorityService;
     }

     public boolean validAuthority(Subject subject) throws ServiceException {
         return authorityService.verifyPermission(subject);
     }

}

