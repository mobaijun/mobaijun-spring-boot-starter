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
package com.mobaijun.redisson.handler;

import com.baomidou.lock.exception.LockFailureException;
import com.mobaijun.common.enums.http.HttpStatus;
import com.mobaijun.common.result.R;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Description: [Redis异常处理器]
 * Author: [mobaijun]
 * Date: [2024/8/14 18:30]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
@RestControllerAdvice
public class RedisExceptionHandler {

    /**
     * 分布式锁Lock4j异常
     */
    @ExceptionHandler(LockFailureException.class)
    public R<Void> handleLockFailureException(LockFailureException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("获取锁失败了'{}',发生Lock4j异常.", requestURI, e);
        return R.failed(HttpStatus.SERVICE_UNAVAILABLE, "业务处理中，请稍后再试...");
    }
}
