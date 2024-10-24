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
package com.mobaijun.sse.controller;

import com.mobaijun.common.result.R;
import com.mobaijun.sse.core.SseEmitterManager;
import com.mobaijun.sse.message.SseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * Description: [sse 控制器]
 * Author: [mobaijun]
 * Date: [2024/10/9 17:52]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@RestController
@RequestMapping("/api")
@Tag(name = "SSE 控制器", description = "SSE 控制器")
@ConditionalOnProperty(value = "sse.enabled", havingValue = "true")
public class SseController implements DisposableBean {

    private final SseEmitterManager sseEmitterManager;

    public SseController(SseEmitterManager sseEmitterManager) {
        this.sseEmitterManager = sseEmitterManager;
    }

    /**
     * 建立 SSE 连接
     *
     * @param tokenValue 连接的令牌值
     * @param userId     用户 ID
     * @return SSEEmitter 连接对象
     */
    @Operation(summary = "建立 SSE 连接")
    @GetMapping(value = "${sse.path}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect(
            @Parameter(description = "连接的令牌值") @RequestParam("tokenValue") String tokenValue,
            @Parameter(description = "用户 ID") @RequestParam("userId") Long userId) {
        return sseEmitterManager.connect(userId, tokenValue);
    }

    /**
     * 关闭 SSE 连接
     *
     * @param tokenValue 连接的令牌值
     * @param userId     用户 ID
     * @return 响应结果
     */
    @Operation(summary = "关闭 SSE 连接")
    @GetMapping(value = "${sse.path}/close")
    public R<Void> close(
            @Parameter(description = "连接的令牌值") @RequestParam("tokenValue") String tokenValue,
            @Parameter(description = "用户 ID") @RequestParam("userId") Long userId) {
        sseEmitterManager.disconnect(userId, tokenValue);
        return R.ok();
    }

    /**
     * 向特定用户发送消息
     *
     * @param userId 目标用户的 ID
     * @param msg    要发送的消息内容
     * @return 响应结果
     */
    @Operation(summary = "向特定用户发送消息")
    @PostMapping(value = "${sse.path}/send")
    public R<Void> send(
            @Parameter(description = "目标用户的 ID") @RequestParam("userId") Long userId,
            @Parameter(description = "要发送的消息内容") @RequestParam("msg") String msg) {
        SseMessage dto = new SseMessage();
        dto.setUserIds(List.of(userId));
        dto.setMessage(msg);
        sseEmitterManager.publishMessage(dto);
        return R.ok();
    }

    /**
     * 向所有用户发送消息
     *
     * @param msg 要发送的消息内容
     * @return 响应结果
     */
    @Operation(summary = "向所有用户发送消息")
    @PostMapping(value = "${sse.path}/sendAll")
    public R<Void> sendAll(
            @Parameter(description = "要发送的消息内容") @RequestParam("msg") String msg) {
        sseEmitterManager.publishAll(msg);
        return R.ok();
    }

    @Override
    public void destroy() {
        // 不需要执行任何操作
    }
}
