package com.hakcerda.shirorbacexample.controller;

import com.hakcerda.shirorbacexample.model.UserVo;
import com.hakcerda.shirorbacexample.model.WebResponse;
import com.hakcerda.shirorbacexample.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author JR Chan
 */
@RestController
public class ExampleController {
    private final UserService userService;

    public ExampleController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public WebResponse login(@RequestParam("username") String username,
                             @RequestParam("password") String password) {
        UserVo userVo = userService.getUserVo(username, password);
        return WebResponse.success(userVo);
    }

    @GetMapping("/require_auth")
    @RequiresAuthentication
    public WebResponse requireAuth() {
        return WebResponse.success("authenticated");
    }

    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public WebResponse requireRole() {
        return WebResponse.success("You are visiting require_role");
    }

    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public WebResponse requirePermission() {
        return WebResponse.success("You are visiting permission require edit,view");
    }
}
