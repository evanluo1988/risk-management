package com.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.domain.User;
import com.springboot.model.UserRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zx on 2020/7/23.
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    IPage<User> findAllUsersByAreaIds(@Param("areaIds") List<Long> areaIds, Page<User> page);
    UserRole findUserWithRoleById(Long id);

    @Update("Update users SET enable='N' WHERE id = #{id}")
    void disableById(@Param("id")Long id);
}
