package com.thciwei.proj.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

//判断当前用户是否具备
@Component
public class MyDecisionManager implements AccessDecisionManager {

    @Override//第三个参数其实就是MyFilter getAttributes方法的返回值
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> collection)
            throws AccessDeniedException, InsufficientAuthenticationException {

        for (ConfigAttribute configAttribute : collection) {
            String needRoles = configAttribute.getAttribute();
          if("ROLE_LOGIN".equals(needRoles)){
              if(authentication instanceof AnonymousAuthenticationToken){
                  throw new AccessDeniedException("尚未登录，请登录！");
              }else{
                  return;
              }
          }
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if(authority.getAuthority().equals(needRoles)){
                    return;
                }
            }
        }
      throw new AccessDeniedException("权限不足，请联系管理员!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
