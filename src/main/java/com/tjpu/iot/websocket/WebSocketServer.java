/**
 * Project Name: tjpu-iot
 * File Name: WebSocketServer
 * Package Name: com.tjpu.iot.websocket
 * Date: 2019-02-25 23:36
 * Copyright (c) 2019, Wang, Haoyue All Rights Reserved.
 */
package com.tjpu.iot.websocket;

import com.tjpu.iot.util.WebSocketUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * ClassName: WebSocketServer
 * Description: TODO
 * Date: 2019-02-25 23:36
 *
 * @author Wang, Haoyue
 * @version V1.0
 * @since JDK 1.8
 */
@Slf4j
@ServerEndpoint(value = "/ws/{userId}")
@Component
public class WebSocketServer {

    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) {
        log.info("[WebSocketServer] onOpen() 客户端已连接,userId: " + userId);
        WebSocketUtil.add(userId, session);
    }

    @OnMessage
    public String onMessage(@PathParam("userId") String userId, String message) {
        log.info("[WebSocketServer] onMessage() 收到客户端消息");
        log.info("[WebSocketServer] onMessage() userId: " + userId + ", message: " + message);
        WebSocketUtil.receive(userId, message);
        return message;
    }

    @OnError
    public void onError(@PathParam("userId") String userId, Session session, Throwable throwable) {
        log.info("[WebSocketServer] onError() 连接出错,userId: " + userId);
        log.info("[WebSocketServer] onError() 错误原因: " + throwable.getMessage());
        WebSocketUtil.remove(userId);
    }

    @OnClose
    public void onClose(@PathParam("userId") String userId, Session session) {
        log.info("[WebSocketServer] onClose() 连接断开,userId: " + userId);
        WebSocketUtil.remove(userId);
    }
}
