package com.tio.app.common.oauth;

import com.tio.app.common.services.ShiroService;
import com.tio.app.sys.entities.SAdmin;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class OAuth2Realm extends AuthorizingRealm {
    @Autowired
    private ShiroService shiroService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("授权验证权限时调用-->OAuth2Realm.doGetAuthorizationInfo() principals : " + principals.getPrimaryPrincipal());
        SAdmin user = (SAdmin) principals.getPrimaryPrincipal();
        Integer userId = user.getId();
        //用户权限列表
        Set<String> permsSet = shiroService.getUserPermissions(userId);
        //用户角色
        Set<String> rolesSet = shiroService.getUserRoles(userId);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        info.addRoles(rolesSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("认证 ---> OAuth2Realm.doGetAuthenticationInfo()" + token.getPrincipal() + " : " + token.getCredentials());
        String accessToken = token.getPrincipal().toString();
        //根据accessToken,获取token中的值
        Map<String, Object> map = TokenManager.decodeToken(accessToken);
        //判断token是否失效
        if (map == null) {
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }
        //从TOKEN中获取用户ID
        Object userId = map.get("user_id");
        if (null == userId) {
            throw new IncorrectCredentialsException("请提供user_id字段");
        }
        //根据用户ID查询用户信息
        SAdmin admin = shiroService.getUser((int) userId);
        //判断账号的状态
        int userStatus = admin.getStatus();
        if (userStatus == 0) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(admin, accessToken, getName());
        return info;
    }
}
