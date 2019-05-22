package com.tjpu.iot.dao;

import com.tjpu.iot.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * insertSelective: 动态添加用户
     *
     * @param user
     * @return
     */
    int insertSelective(User user);

    /**
     * deleteByPrimaryKey: 通过用户编号删除指定的用户
     *
     * @param userId
     * @return
     */
    int deleteByPrimaryKey(String userId);

    /**
     * updateByPrimaryKeySelective: 动态更新用户信息
     *
     * @param user
     * @return
     */
    int updateByPrimaryKeySelective(User user);

    /**
     * updateStateOn: 通过用户编号更新用户的登录信息
     *
     * @param userId
     * @return
     */
    int updateStateOn(String userId);

    /**
     * updateStateOff: 通过用户编号更新用户的登录信息
     *
     * @param userId
     * @return
     */
    int updateStateOff(String userId);

    /**
     * selectByPrimaryKey: 通过用户编号查询指定的用户
     *
     * @param userId
     * @return
     */
    User selectByPrimaryKey(String userId);

    /**
     * selectByMobile: 通过用户手机号查询指定的用户
     *
     * @param userMobile
     * @return
     */
    User selectByMobile(String userMobile);

    /**
     * queryAllUser: 查询所有用户
     *
     * @return
     */
    List<User> queryAllUser();
}