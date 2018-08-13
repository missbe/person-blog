package cn.missbe.missbe_www.shiro.token;

import cn.missbe.missbe_www.entity.Permission;
import cn.missbe.missbe_www.entity.Role;
import cn.missbe.missbe_www.entity.User;
import cn.missbe.missbe_www.service.UserService;
import cn.missbe.missbe_www.shiro.token.manager.TokenManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class SampleRealm extends AuthorizingRealm {

    @Autowired
	UserService userService;

    /**
     * 认证信息，主要针对用户登录，
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException {

        ShiroToken token = (ShiroToken) authcToken;
        User user = userService.login(token.getUsername(), token.getPswd());
        if (null == user) {
            throw new AccountException("帐号或密码不正确！");
        } else if (user.getStatus() == 1) {
            throw new DisabledAccountException("账户已被禁用。");
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        Long userId = TokenManager.getUserId();
        User user = userService.findById(userId);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();
        for (Role role : user.getRoles()) {
            roles.add(role.getName());
            for (Permission p : role.getPmss()) {
                permissions.add(p.getPermission());
            }
        }
        info.setRoles(roles);
        info.setStringPermissions(permissions);
        return info;
    }

    /**
     * 清空当前用户权限信息
     */
    public void clearCachedAuthorizationInfo() {
        PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 指定principalCollection 清除
     */
    public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }
}
