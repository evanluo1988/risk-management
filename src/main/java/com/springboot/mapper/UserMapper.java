package com.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zx on 2020/7/23.
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    @Update("Update t_users SET email=#{email} WHERE name = #{name}")
    void updateEmailByName(@Param("name")String name, @Param("email")String email);

    User findUserWithRoleById(Long id);
}
