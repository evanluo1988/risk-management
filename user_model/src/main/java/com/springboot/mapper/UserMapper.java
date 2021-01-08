package com.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.domain.User;
import com.springboot.model.UserInfo;
import com.springboot.model.UserRoleDomain;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zx on 2020/7/23.
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    IPage<UserInfo> findAllUsersByAreaIds(@Param("loginName")String loginName,@Param("userName")String userName, @Param("areaId")Long areaId, @Param("areaIds") List<Long> areaIds, Page<User> page);
    UserRoleDomain findUserWithRoleById(Long id);

    @Update("Update users SET enable='N' WHERE id = #{id}")
    void disableById(@Param("id")Long id);
}
