package com.hakcerda.shirorbacexample.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.hakcerda.shirorbacexample.model.*;
import com.hakcerda.shirorbacexample.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Slf4j
public class JWTReleam extends AuthorizingRealm {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     * 我们可以直接查库来获取用户的权限，如果希望减少查询开销对实时性要求不高
     * 也可以将用户角色和权限写在payGround上来获取，配合JWT的过期机制定时更新
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = principals.toString();

        UserDetail userDetail = userService.getUserDetail(userName);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(userDetail.getRoleSet().stream().map(Role::getCode).collect(Collectors.toSet()));

        simpleAuthorizationInfo.addStringPermissions(userDetail.getPermissionSet().stream().map(Permission::getPermissionCode).collect(Collectors.toSet()));
        return simpleAuthorizationInfo;
    }

    /**
     * 这个方法顾名思义，获取授权的认证信息，对于token的合法校验可以在这一步完成
     * 也可以实现{@link CredentialsMatcher}接口来完成
     *
     * 这样做的细微差别是，如果你的获取用户信息是个IO密集的操作，
     * shiro可以为你缓存这个结果，直接由CredentialsMatcher接口来判断
     *
     * For most dataSources, this means just 'pulling' authentication data for an associated subject/user and nothing
     * more and letting Shiro do the rest.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JwtUtils.getClaim(token, JwtUtils.USERNAME_KEY);
        if (username == null) {
            throw new AuthenticationException("token invalid");
        }
        try {
            JwtUtils.verify(token, username, username);
        }catch (JWTVerificationException e){
            throw new AuthenticationException(e);
        }catch (Exception e){
            log.error("verify token error", e);
            throw new AuthenticationException(e);
        }

        return new SimpleAuthenticationInfo(username, token, "JWTRealm");

    }
}
