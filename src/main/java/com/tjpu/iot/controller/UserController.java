package com.tjpu.iot.controller;

import com.tjpu.iot.common.ResponseResult;
import com.tjpu.iot.dto.UserLoginDto;
import com.tjpu.iot.pojo.User;
import com.tjpu.iot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/tjpu/iot/user/")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * add: 添加用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "user", method = RequestMethod.POST)
    public ResponseResult add(@RequestBody User user) {
        log.info("[UserController] add() 进入添加用户方法");
        return userService.addUser(user);
    }

    /**
     * delete: 删除用户
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "{userId}", method = RequestMethod.DELETE)
    public ResponseResult delete(@PathVariable("userId") String userId) {
        log.info("[UserController] delete() 进入删除用户方法");
        return userService.deleteUser(userId);
    }

    /**
     * update: 更新用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "user", method = RequestMethod.PUT)
    public ResponseResult update(@RequestBody User user) {
        log.info("[UserController] update() 进入更新用户方法");
        return userService.updateUser(user);
    }

    /**
     * queryOne: 查询指定用户
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "{userId}", method = RequestMethod.GET)
    public ResponseResult queryOne(@PathVariable("userId") String userId) {
        log.info("[UserController] queryOne() 进入查询用户方法");
        return userService.queryUser(userId);
    }

    /**
     * queryAll: 查询所有用户
     *
     * @return
     */
    @RequestMapping(value = "user", method = RequestMethod.GET)
    public ResponseResult queryAll() {
        log.info("[UserController] queryAll() 进入查询所有用户方法");
        return userService.queryAllUsers();
    }

    /**
     * login: 用户登录
     *
     * @param userLoginDto
     * @param session
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseResult login(@RequestBody UserLoginDto userLoginDto, HttpSession session) {
        log.info("[UserController] login() 进入用户登陆方法");
        return userService.userLogin(userLoginDto, session);
    }

    /**
     * logout: 用户注销
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.PUT)
    public ResponseResult logout(String userId) {
        log.info("[UserController] logout() 进入用户注销方法");
        return userService.userLogout(userId);
    }
}
