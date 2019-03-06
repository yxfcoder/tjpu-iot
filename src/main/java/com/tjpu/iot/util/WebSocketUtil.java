package com.tjpu.iot.util;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class WebSocketUtil {

    private static Map<String, Session> clients = new ConcurrentHashMap<>();

    public static void add(String userId, Session session) {
        clients.put(userId, session);
        log.info("[WebSocketUtil] add() 当前连接数: " + clients.size());
    }

    public static void receive(String userId, String message) {
        log.info("[WebSocketUtil] receive() 收到来自 " + userId + " 的消息: " + message);
        log.info("当前连接数: " + clients.size());
    }

    public static void remove(String userId) {
        clients.remove(userId);
        log.info("[WebSocketUtil] remove() 当前连接数: " + clients.size());
    }

    public static boolean sendMessage(String userId, String message) {
        log.info("[WebSocketUtil] sendMessage() 当前连接数: " + clients.size());
        if (clients.get(userId) == null) {
            return false;
        } else {
            clients.get(userId).getAsyncRemote().sendText(message);
            return true;
        }
    }
}
