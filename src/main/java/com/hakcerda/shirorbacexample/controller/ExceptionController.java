package com.hakcerda.shirorbacexample.controller;

import com.hakcerda.shirorbacexample.exception.UnauthorizedException;
import com.hakcerda.shirorbacexample.model.WebResponse;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenjuanrong
 */
@RestControllerAdvice
public class ExceptionController {


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public WebResponse handle401() {
        return WebResponse.failUnauthorized("用户未授权");
    }



    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthorizationException.class)
    public WebResponse handle403() {
        return WebResponse.failWithForbidden("没有访问权限");
    }
}

