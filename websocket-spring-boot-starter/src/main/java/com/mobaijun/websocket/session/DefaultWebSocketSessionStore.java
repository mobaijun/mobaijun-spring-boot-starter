/*
 * Copyright (C) 2022 [www.mobaijun.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mobaijun.websocket.session;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

/**
 * Description: 默认的 WebSocketSession 存储器
 * Author: [mobaijun]
 * Date: [2024/8/23 9:28]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
public class DefaultWebSocketSessionStore implements WebSocketSessionStore {

    /**
     * 生成 session key 的接口
     */
    private final SessionKeyGenerator sessionKeyGenerator;

    /**
     * 存储 session key 和 session 的映射关系
     */
    private final ConcurrentHashMap<Object, Map<String, WebSocketSession>> sessionKeyToWsSessions = new ConcurrentHashMap<>();

    /**
     * 构造方法
     *
     * @param sessionKeyGenerator 生成 session key 的接口
     */
    public DefaultWebSocketSessionStore(SessionKeyGenerator sessionKeyGenerator) {
        this.sessionKeyGenerator = sessionKeyGenerator;
    }

    /**
     * 添加一个 wsSession
     *
     * @param wsSession 待添加的 WebSocketSession
     */
    @Override
    public void addSession(WebSocketSession wsSession) {
        Object sessionKey = this.sessionKeyGenerator.sessionKey(wsSession);
        Map<String, WebSocketSession> sessions = this.sessionKeyToWsSessions.get(sessionKey);
        if (sessions == null) {
            sessions = new ConcurrentHashMap<>();
            this.sessionKeyToWsSessions.putIfAbsent(sessionKey, sessions);
            sessions = this.sessionKeyToWsSessions.get(sessionKey);
        }
        sessions.put(wsSession.getId(), wsSession);
    }

    /**
     * 删除一个 session
     *
     * @param session WebSocketSession
     */
    @Override
    public void removeSession(WebSocketSession session) throws IOException {
        Object sessionKey = this.sessionKeyGenerator.sessionKey(session);
        String wsSessionId = session.getId();

        Map<String, WebSocketSession> sessions = this.sessionKeyToWsSessions.get(sessionKey);
        if (sessions != null) {
            try (WebSocketSession webSocketSession = sessions.remove(wsSessionId)) {
                boolean result = webSocketSession != null;
                if (log.isDebugEnabled()) {
                    log.debug("Removal of " + wsSessionId + " was " + result);
                }
            }

            if (sessions.isEmpty()) {
                this.sessionKeyToWsSessions.remove(sessionKey);
                if (log.isDebugEnabled()) {
                    log.debug("Removed the corresponding HTTP Session for " + wsSessionId
                            + " since it contained no WebSocket mappings");
                }
            }
        }
    }

    /**
     * 获取当前所有在线的 session
     *
     * @return Collection<WebSocketSession> session集合
     */
    @Override
    public Collection<WebSocketSession> getSessions() {
        return this.sessionKeyToWsSessions.values()
                .stream()
                .flatMap(x -> x.values().stream())
                .collect(Collectors.toList());
    }

    /**
     * 根据指定的 sessionKey 获取对应的 wsSessions
     *
     * @param sessionKey wsSession 标识
     * @return Collection<WebSocketSession> websocket session集合
     */
    @Override
    public Collection<WebSocketSession> getSessions(Object sessionKey) {
        Map<String, WebSocketSession> sessions = this.sessionKeyToWsSessions.get(sessionKey);
        if (sessions == null) {
            log.warn("根据指定的sessionKey: {} 获取对应的wsSessions为空!", sessionKey);
            return Collections.emptyList();
        }
        return sessions.values();
    }

    /**
     * 获取所有的 sessionKeys
     *
     * @return sessionKey 集合
     */
    @Override
    public Collection<Object> getSessionKeys() {
        return this.sessionKeyToWsSessions.keySet();
    }
}
