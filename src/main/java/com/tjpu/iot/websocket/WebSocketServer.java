//package com.tjpu.iot.websocket;
//
//import com.tjpu.iot.util.WebSocketUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.websocket.*;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//
//@Slf4j
//@ServerEndpoint(value = "/ws/{userId}")
//@Component
//public class WebSocketServer {
//
//    @OnOpen
//    public void onOpen(@PathParam("userId") String userId, Session session) {
//        log.info("[WebSocketServer] onOpen() 客户端已连接,userId: " + userId);
//        WebSocketUtil.add(userId, session);
//    }
//
//    @OnMessage
//    public String onMessage(@PathParam("userId") String userId, String message) {
//        log.info("[WebSocketServer] onMessage() 收到客户端消息");
//        log.info("[WebSocketServer] onMessage() userId: " + userId + ", message: " + message);
//        WebSocketUtil.receive(userId, message);
//        return message;
//    }
//
//    @OnError
//    public void onError(@PathParam("userId") String userId, Session session, Throwable throwable) {
//        log.info("[WebSocketServer] onError() 连接出错,userId: " + userId);
//        log.info("[WebSocketServer] onError() 错误原因: " + throwable.getMessage());
//        WebSocketUtil.remove(userId);
//    }
//
//    @OnClose
//    public void onClose(@PathParam("userId") String userId, Session session) {
//        log.info("[WebSocketServer] onClose() 连接断开,userId: " + userId);
//        WebSocketUtil.remove(userId);
//    }
//}
