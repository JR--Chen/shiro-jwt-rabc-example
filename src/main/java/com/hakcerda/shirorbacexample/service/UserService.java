package com.hakcerda.shirorbacexample.service;

import com.hakcerda.shirorbacexample.dao.PermissionDao;
import com.hakcerda.shirorbacexample.dao.RoleDao;
import com.hakcerda.shirorbacexample.dao.UserDao;
import com.hakcerda.shirorbacexample.exception.UnauthorizedException;
import com.hakcerda.shirorbacexample.model.*;
import com.hakcerda.shirorbacexample.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JR Chan
 */
@Service
public class UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PermissionDao permissionDao;


    public UserService(UserDao userDao, RoleDao roleDao, PermissionDao permissionDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.permissionDao = permissionDao;
    }


    public UserDetail getUserDetail(String userName) {
        User user = userDao.selectUserByName(userName);
        return getUserDetail(user);
    }


    public UserDetail getUserDetail(@NotNull User user) {

        List<Role> roles = roleDao.selectByUser(user);
        List<Permission> permissions = permissionDao.selectByRole(new HashSet<>(roles));

        return new UserDetail().setUserName(user.getUsername())
                .setPermissionSet(new HashSet<>(permissions))
                .setRoleSet(new HashSet<>(roles));
    }


    public UserVo getUserVo(String userName, String password) {

        User user = userDao.selectUserByName(userName);

        if (user.getPassword().equals(password)) {
            UserDetail userDetail = getUserDetail(user);

            return new UserVo()
                    .setUserName(userDetail.getUserName())
                    .setPermissionSet(userDetail.getPermissionSet())
                    .setRoleSet(userDetail.getRoleSet())
                    .setToken(JwtUtils.sign(userDetail.getUserName(), userDetail.getUserName()));
        }
        throw new UnauthorizedException();
    }
}
