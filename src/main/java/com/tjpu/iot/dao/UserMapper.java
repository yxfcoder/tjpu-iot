package com.tjpu.iot.dao;

import com.tjpu.iot.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    int insertSelective(User user);

    int deleteByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(User user);

    int updateStateOn(String userId);

    int updateStateOff(String userId);

    User selectByPrimaryKey(String userId);

    User selectByMobile(String userMobile);

    List<User> queryAllUser();
}