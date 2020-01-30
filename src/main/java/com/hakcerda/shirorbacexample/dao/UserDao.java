package com.hakcerda.shirorbacexample.dao;

import com.hakcerda.shirorbacexample.mapper.UserDynamicSqlSupport;
import com.hakcerda.shirorbacexample.mapper.UserMapper;
import com.hakcerda.shirorbacexample.model.User;
import org.mybatis.dynamic.sql.select.MyBatis3SelectModelAdapter;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

/**
 * @author JR Chan
 */
@Service
public class UserDao {
    private final UserMapper userMapper;

    public UserDao(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User selectUserByName(String userName){
        List<User> user = userMapper.selectByExample()
                .where(UserDynamicSqlSupport.username, isEqualTo(userName))
                .build().execute();

        return user.get(0);


    }
}
