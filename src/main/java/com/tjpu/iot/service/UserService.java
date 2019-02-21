package com.tjpu.iot.service;

import com.tjpu.iot.common.ResponseResult;
import com.tjpu.iot.dto.UserLoginDto;
import com.tjpu.iot.pojo.User;

public interface UserService {
    ResponseResult addUser(User user);

    ResponseResult deleteUser(String userId);

    ResponseResult updateUser(User user);

    ResponseResult queryUser(String userId);

    ResponseResult queryAllUsers();

    ResponseResult userLogin(UserLoginDto userLoginDto);
}
