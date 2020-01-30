package com.hakcerda.shirorbacexample.dao;

import com.hakcerda.shirorbacexample.mapper.*;
import com.hakcerda.shirorbacexample.model.Permission;
import com.hakcerda.shirorbacexample.model.Role;
import com.hakcerda.shirorbacexample.model.User;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

/**
 * @author JR Chan
 */
@Service
public class PermissionDao {
    private final PermissionMapper permissionMapper;

    public PermissionDao(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }


    public List<Permission> selectByUser(User user) {
        SelectStatementProvider manyPermission =
                select(PermissionDynamicSqlSupport.id, PermissionDynamicSqlSupport.permissionCode, PermissionDynamicSqlSupport.permissionName)
                        .from(PermissionDynamicSqlSupport.permission)
                        .join(RolePermissionDynamicSqlSupport.rolePermission).on(RolePermissionDynamicSqlSupport.permissionId, equalTo(PermissionDynamicSqlSupport.id))
                        .join(UserRoleDynamicSqlSupport.userRole).on(UserRoleDynamicSqlSupport.roleId, equalTo(RolePermissionDynamicSqlSupport.roleId))
                        .where(UserRoleDynamicSqlSupport.userId, isEqualTo(user.getId()))
                        .build()
                        .render(RenderingStrategies.MYBATIS3);

        return permissionMapper.selectMany(manyPermission);
    }


    public List<Permission> selectByRole(Role role) {
        return permissionMapper.selectByExample()
                .join(RolePermissionDynamicSqlSupport.rolePermission).on(PermissionDynamicSqlSupport.id, equalTo(RolePermissionDynamicSqlSupport.permissionId))
                .where(RolePermissionDynamicSqlSupport.roleId, isEqualTo(role.getId()))
                .build()
                .execute();

    }


    public List<Permission> selectByRole(Set<Role> role) {
        return permissionMapper.selectByExample()
                .join(RolePermissionDynamicSqlSupport.rolePermission).on(PermissionDynamicSqlSupport.id, equalTo(RolePermissionDynamicSqlSupport.permissionId))
                .where(RolePermissionDynamicSqlSupport.roleId, isIn(role.stream().map(Role::getId).collect(Collectors.toList())))
                .build()
                .execute();

    }
}
