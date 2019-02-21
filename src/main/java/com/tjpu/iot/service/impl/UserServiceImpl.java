package com.tjpu.iot.service.impl;

import com.tjpu.iot.common.ResponseResult;
import com.tjpu.iot.common.StatusCode;
import com.tjpu.iot.dao.UserMapper;
import com.tjpu.iot.dto.UserLoginDto;
import com.tjpu.iot.pojo.User;
import com.tjpu.iot.service.UserService;
import com.tjpu.iot.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public ResponseResult addUser(User user) {
        log.info("[UserServiceImpl] addUser() 进入添加用户方法");
        if (user == null) {
            log.info("[UserServiceImpl] addUser() 参数为空");
            return new ResponseResult(false, "参数为空", StatusCode.ERROR_INVALID_RREQUEST);
        }
        try {
            if (userMapper.insertSelective(user) == 0) {
                log.info("[UserServiceImpl] addUser() 添加用户信息执行失败");
                return new ResponseResult(false, "添加用户信息失败", StatusCode.OPERATIONERROR);
            }
            log.info("[UserServiceImpl] addUser() 添加用户信息成功: " + user.toString());
            return new ResponseResult(true, "添加用户信息成功", StatusCode.SUCCESS_POST_PUT_PATCH);
        } catch (DuplicateKeyException e) {
            log.info("[UserServiceImpl] addUser() 添加用户信息出错，违反唯一约束");
            return new ResponseResult(false, "用户字段重复", StatusCode.OPERATIONERROR);
        }
    }

    @Override
    public ResponseResult deleteUser(String userId) {
        log.info("[UserServiceImpl] deleteUser() 进入删除用户方法");
        if (StringUtils.isBlank(userId)) {
            log.info("[UserServiceImpl] deleteUser() userId为空，不能删除用户信息");
            return new ResponseResult(false, "参数为空", StatusCode.ERROR_INVALID_RREQUEST);
        }
        int result = userMapper.deleteByPrimaryKey(userId);
        if (result == 0) {
            log.info("[UserServiceImpl] deleteUser() 用户不存在，userId: " + userId);
            return new ResponseResult(false, "用户不存在", StatusCode.OPERATIONERROR);
        }
        log.info("[UserServiceImpl] deleteUser() 删除用户信息成功: " + userId);
        return new ResponseResult(true, "删除用户信息成功", StatusCode.SUCCESS_DELETE);
    }

    @Override
    public ResponseResult updateUser(User user) {
        log.info("[UserServiceImpl] updateUser() 进入更新用户方法");
        if (user == null) {
            log.info("[UserServiceImpl] updateUser() 参数为空");
            return new ResponseResult(false, "参数为空", StatusCode.ERROR_INVALID_RREQUEST);
        }
        if (userMapper.updateByPrimaryKeySelective(user) == 0) {
            log.info("[UserServiceImpl] updateUser() 更新用户执行失败");
            return new ResponseResult(false, "更新用户失败", StatusCode.OPERATIONERROR);
        }
        log.info("[UserServiceImpl] updateUser() 更新用户成功: " + user.toString());
        return new ResponseResult(true, "更新用户成功", StatusCode.SUCCESS_POST_PUT_PATCH);
    }

    @Override
    public ResponseResult queryUser(String userId) {
        log.info("[UserServiceImpl] queryUser() 进入查询用户方法");
        if (StringUtils.isBlank(userId)) {
            log.info("[UserServiceImpl] queryUser() userId为空，不能查询用户");
            return new ResponseResult(false, "参数为空", StatusCode.ERROR_INVALID_RREQUEST);
        }
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            log.info("[UserServiceImpl] queryUser() 用户不存在，userId: " + userId);
            return new ResponseResult(false, "用户不存在", StatusCode.OPERATIONERROR);
        }
        UserInfoVO userInfoVO = userToUserInfoVO(user);
        log.info("[UserServiceImpl] queryUser() 查询用户成功: " + userInfoVO.toString());
        return new ResponseResult(userInfoVO, true, "查询用户信息成功", StatusCode.SUCCESS_GET);
    }

    @Override
    public ResponseResult queryAllUsers() {
        log.info("[UserServiceImpl] queryAllUsers() 进入查询所有用户方法");
        List<User> userList = userMapper.queryAllUser();
        if (CollectionUtils.isEmpty(userList)) {
            log.info("[UserServiceImpl] queryAllUsers() 没有查到用户");
            return new ResponseResult(false, "没有查到用户", StatusCode.OPERATIONERROR);
        }
        List<UserInfoVO> userInfoVOList = new ArrayList<>();
        for (User user : userList) {
            UserInfoVO userInfoVO = userToUserInfoVO(user);
            userInfoVOList.add(userInfoVO);
        }
        log.info("[UserServiceImpl] queryAllUsers() 查询所有用户成功，共查到" + userInfoVOList.size() + "条用户数据");
        return new ResponseResult(userInfoVOList, true, "查询用户成功", StatusCode.SUCCESS_GET);
    }

    @Override
    public ResponseResult userLogin(UserLoginDto userLoginDto) {
        log.info("[UserServiceImpl] userLogin() 进入用户登陆方法");
        if (userLoginDto == null) {
            log.info("[UserServiceImpl] userLogin() 参数为空，不能登陆");
            return new ResponseResult(false, "参数为空", StatusCode.ERROR_INVALID_RREQUEST);
        }
        if (userLoginDto.getMobile() == null) {
            log.info("[UserServiceImpl] userLogin() 用户名为空，不能登陆");
            return new ResponseResult(false, "用户名不能为空", StatusCode.ERROR_INVALID_RREQUEST);
        }
        if (userLoginDto.getPassword() == null) {
            log.info("[UserServiceImpl] userLogin() 密码为空，不能登陆");
            return new ResponseResult(false, "密码不能为空", StatusCode.ERROR_INVALID_RREQUEST);
        }
        User user = userMapper.selectByMobile(userLoginDto.getMobile());
        if (user == null) {
            log.info("[UserServiceImpl] userLogin() 没有查到用户");
            return new ResponseResult(false, "用户不存在", StatusCode.OPERATIONERROR);
        }
        if (!user.getUserPassword().equals(userLoginDto.getPassword())) {
            log.info("[UserServiceImpl] userLogin() 密码错误");
            return new ResponseResult(false, "密码不正确", StatusCode.OPERATIONERROR);
        }
        log.info("[UserServiceImpl] userLogin() 登陆成功");
        return new ResponseResult(user, true, "登陆成功", StatusCode.SUCCESS_GET);
    }

    private UserInfoVO userToUserInfoVO(User user) {
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUserId(user.getUserId());
        userInfoVO.setUserMobile(user.getUserMobile());
        userInfoVO.setUserName(user.getUserName());
        userInfoVO.setUserCompany(user.getUserCompany());
        userInfoVO.setUserLocal(user.getUserLocal());
        userInfoVO.setUserState(user.getUserState());
        userInfoVO.setUserPermission(user.getUserPermission());
        return userInfoVO;
    }
}
