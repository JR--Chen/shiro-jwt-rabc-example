package com.hakcerda.shirorbacexample.controller;

import com.hakcerda.shirorbacexample.exception.UnauthorizedException;
import com.hakcerda.shirorbacexample.model.JWTToken;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chenjuanrong
 */
public class RestFilter extends BasicHttpAuthenticationFilter {

    public static final String NAME = "JWTFilter";

    /**
     * 检查是否登陆请求
     */
    private boolean isLoginAttempt(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;
        String servletPath = req.getServletPath();
        return "/login".equals(servletPath);
    }

    /**
     * 这个方法是对处理没有获得授权的请求
     * 如果是登陆的请求的我们直接放行，也可以在配置文件中为登陆的url配置直接放行
     * 对于需要校验token的接口，用subject login来校验是否能获取系统的权限
     *
     * @param request  the incoming <code>ServletRequest</code>
     * @param response the outgoing <code>ServletResponse</code>
     * @return true if the request should be processed; false if the request should not continue to be processed
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        if(!isLoginAttempt(request)){
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String token = httpServletRequest.getParameter("token");

            if(StringUtils.isEmpty(token)){
                return false;
            }
            getSubject(request, response).login(new JWTToken(token));
        }

        return true;

    }
}
