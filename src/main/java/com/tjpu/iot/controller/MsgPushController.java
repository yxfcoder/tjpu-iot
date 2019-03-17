package com.tjpu.iot.controller;

import com.tjpu.iot.common.ResponseResult;
import com.tjpu.iot.common.StatusCode;
import com.tjpu.iot.util.WebSocketUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tjpu/iot/msg/")
public class MsgPushController {

    @RequestMapping(value = "{userId}", method = RequestMethod.POST)
    public ResponseResult msgPush(@PathVariable("userId") String userId, String message) {
        if (WebSocketUtil.sendMessage(userId, message)) {
            return new ResponseResult(true, "消息推送成功", StatusCode.SUCCESS_POST_PUT_PATCH);
        } else {
            return new ResponseResult(false, "消息推送失败", StatusCode.OPERATIONERROR);
        }
    }
}
