package com.hakcerda.shirorbacexample.dao;

import com.hakcerda.shirorbacexample.mapper.*;
import com.hakcerda.shirorbacexample.model.Role;
import com.hakcerda.shirorbacexample.model.User;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

/**
 * @author JR Chan
 */
@Service
public class RoleDao {
    @Autowired
    private RoleMapper roleMapper;




    public List<Role> selectByUser(User user){

        return roleMapper.selectByExample()
                .join(UserRoleDynamicSqlSupport.userRole).on(UserRoleDynamicSqlSupport.roleId, equalTo(RoleDynamicSqlSupport.id))
                .where(UserRoleDynamicSqlSupport.userId, isEqualTo(user.getId()))
                .build()
                .execute();

    }
}
