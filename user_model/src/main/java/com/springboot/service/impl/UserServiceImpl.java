package com.springboot.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.springboot.constant.GlobalConstants;
import com.springboot.domain.*;
import com.springboot.enums.AreaTypeEnum;
import com.springboot.enums.EnableEnum;
import com.springboot.enums.RoleEnum;
import com.springboot.exception.ServiceException;
import com.springboot.mapper.UserMapper;
import com.springboot.model.RolePerm;
import com.springboot.page.PageIn;
import com.springboot.page.Pagination;
import com.springboot.ret.ReturnT;
import com.springboot.service.AreaService;
import com.springboot.service.RoleService;
import com.springboot.service.UserRoleService;
import com.springboot.service.UserService;
import com.springboot.util.ConvertUtils;
import com.springboot.utils.HttpServletLocalThread;
import com.springboot.utils.ReturnTUtils;
import com.springboot.utils.RoleUtils;
import com.springboot.utils.UserAuthInfoContext;
import com.springboot.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.internal.guava.Sets;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import com.springboot.model.UserRoleDomain;

/**
 * @author evan
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AreaService areaService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;

    @Override
    public Pagination<UserVo> findUsers(PageIn pageIn) {
        //根据当前权限过滤用户
        //List<Long> areaIds = areaService.findAreaIdsById(UserAuthInfoContext.getAreaId());
        List<Long> areaIds = new ArrayList<>();
        areaIds.add(1L);
        IPage<User> userPage = userMapper.findAllUsersByAreaIds(areaIds, pageIn.convertPage());
        List<User> users = userPage.getRecords();
        List<UserVo> userVos = new ArrayList<>();
        for (User user : users) {
            userVos.add(convertUserToUserVo(user));
        }

        return Pagination.of(userVos, userPage.getTotal());
    }

    private UserVo convertUserToUserVo(User user) {
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }

    @Override
    public ReturnT<UserWithRoleVo> findWithRoleById(Long id) {
        UserRoleDomain user = userMapper.findUserWithRoleById(id);
        if (ObjectUtils.isEmpty(user)) {
            return ReturnTUtils.getReturnT(new UserWithRoleVo());
        }
        return ReturnTUtils.getReturnT(convertUserToUserWithRoleVo(user));
    }

    private UserWithRoleVo convertUserToUserWithRoleVo(UserRoleDomain user) {
        UserWithRoleVo vo = new UserWithRoleVo();
        BeanUtils.copyProperties(user, vo);
        Set<RoleVo> roleVoSet = Sets.newHashSet();
        Set<PermVo> permVoSet = Sets.newHashSet();
        for (RolePerm role : user.getRoleList()) {
            RoleVo roleVo = new RoleVo();
            BeanUtils.copyProperties(role, roleVo);
            roleVoSet.add(roleVo);

            for (Permission permission : role.getPermissionList()) {
                PermVo permVo = new PermVo();
                BeanUtils.copyProperties(permission, permVo);
                permVoSet.add(permVo);
            }
        }
        vo.setRoleVoSet(roleVoSet);
        vo.setPermVoSet(permVoSet);
        return vo;
    }

    @Override
    public ReturnT<UserVo> getById(Long id) {
        User user = userMapper.selectById(id);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return ReturnTUtils.getReturnT(userVo);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void update(User user) {
        userMapper.updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void login(RegUserVo userVo) {
        log.info("login param:{}", JSON.toJSONString(userVo));
        User user = getUserByUsername(userVo.getUserName());
        //用户不存在
        if (Objects.isNull(user)) {
            throw new ServiceException("用户名或密码错误");
        }
        //密码错误
        if (!BCrypt.checkpw(userVo.getPassword(), user.getPassword())) {
            throw new ServiceException("用户名或密码错误");
        }
        log.debug("userName password valid success");
        HttpServletRequest request = HttpServletLocalThread.getRequest();
        //将session和业务用户关联
        request.getSession().setAttribute(GlobalConstants.USER_ID, user.getId());
        log.debug("login success userId:{}", user.getId());
    }

    @Override
    public void logout() {
        HttpServletRequest request = HttpServletLocalThread.getRequest();
        HttpSession session = request.getSession();
        Long userId;
        if (Objects.nonNull(userId = (Long) session.getAttribute(GlobalConstants.USER_ID))) {
            log.debug("user logout , userId:{}", userId);
            session.invalidate();
        } else {
            throw new ServiceException("未登录不能登出！");
        }
    }

    @Override
    public User getUserEntityById(Long userId) {
        if (Objects.isNull(userId)) {
            return null;
        }

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getId, userId)
                .eq(User::getEnable, EnableEnum.Y.getCode());
        return userMapper.selectOne(queryWrapper);
    }

    /**
     * 根据用户名查询用户
     *
     * @param userName 用户名
     * @return 用户信息
     */
    private User getUserByUsername(String userName) {
        if (StringUtils.isEmpty(userName)) {
            return null;
        }

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getUserName, userName.trim())
                .eq(User::getEnable, EnableEnum.Y.getCode());
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void deleteById(Long id) {
        userMapper.disableById(id);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void create(RegUserVo userVo) {
        //如果没输入密码 填充默认密码 否则加密用户输入密码
        if (StringUtils.isEmpty(userVo.getPassword())) {
            userVo.setPassword(BCrypt.hashpw(GlobalConstants.DEFAULT_PASSWORD, BCrypt.gensalt()));
        } else {
            userVo.setPassword(BCrypt.hashpw(userVo.getPassword(), BCrypt.gensalt()));
        }

        //校验area
        Area areaById = areaService.getById(userVo.getAreaId());
        checkOpAreaPermissionEnough(areaById);

        //convert and store user
        User user = ConvertUtils.sourceToTarget(userVo, User.class);
        userMapper.insert(user);

        //store role
        UserRole userRole = buildUserRole(user,judgeRoleByArea(areaById));
        userRoleService.save(userRole);
    }

    private UserRole buildUserRole(User user, Role role) {
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId())
                .setRoleId(role.getId());
        return userRole;
    }

    private Role judgeRoleByArea(Area area) {
        if (Objects.isNull(area)) {
            throw new ServiceException("区域信息错误");
        }

        AreaTypeEnum areaTypeEnum = AreaTypeEnum.typeOf(area.getType());
        return roleService.getRoleByName(AreaTypeEnum.R == areaTypeEnum ? RoleEnum.REGION_ADMIN.getName() : RoleEnum.STREET_ADMIN.getName());
    }

    /**
     * 校验当前登录用户操作此区域的权限是否足够
     *
     * @param area 区域
     */
    private void checkOpAreaPermissionEnough(Area area) {
        if (Objects.isNull(area)) {
            throw new ServiceException("区域不存在");
        }

        Collection<RolePerm> rolePerms = UserAuthInfoContext.getRolePerms();
        RolePerm highestLevelRole = RoleUtils.getHighestLevelRole(rolePerms);
        if (Objects.isNull(highestLevelRole)) {
            throw new ServiceException("当前用户角色错误");
        }

        RoleEnum highestLevelRoleEnum = RoleEnum.nameOf(highestLevelRole.getRoleName());
        AreaTypeEnum areaTypeEnum = AreaTypeEnum.typeOf(area.getType());

        //需要最低的管理员等级
        RoleEnum acquireLowestLevelRoleEnum;
        if (AreaTypeEnum.R == areaTypeEnum) {
            acquireLowestLevelRoleEnum = RoleEnum.SYS_ADMIN;
        } else if (AreaTypeEnum.S == areaTypeEnum) {
            acquireLowestLevelRoleEnum = RoleEnum.REGION_ADMIN;
        } else {
            throw new ServiceException("区域类型错误");
        }
        //权限校验
        if (highestLevelRoleEnum.ordinal() > acquireLowestLevelRoleEnum.ordinal()) {
            throw new ServiceException("没有建此区域用户的权限");
        }
    }


}
