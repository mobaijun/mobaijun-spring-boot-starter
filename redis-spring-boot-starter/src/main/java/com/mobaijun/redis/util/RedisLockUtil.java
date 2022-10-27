/*
 * Copyright (C) 2022 www.mobaijun.com
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
package com.mobaijun.redis.util;

import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;

import java.util.concurrent.TimeUnit;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: RedisLockUtil
 * 类描述： Redis 锁
 *
 * @author MoBaiJun 2022/4/28 16:06
 */
public class RedisLockUtil {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisLockUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return this.redisTemplate;
    }

    private static final byte[] SCRIPT_RELEASE_LOCK =
            "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end".getBytes();

    public synchronized Boolean tryLock(String key, String requestId, long expire) {
        return redisTemplate.execute((RedisCallback<Boolean>)
                redisConnection -> redisConnection.set(key.getBytes(),
                        requestId.getBytes(), Expiration.from(expire, TimeUnit.SECONDS),
                        RedisStringCommands.SetOption.SET_IF_ABSENT));
    }

    public synchronized void releaseLock(String key, String requestId) {
        redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> redisConnection.eval(SCRIPT_RELEASE_LOCK, ReturnType.BOOLEAN, 1, key.getBytes(), requestId.getBytes()));
    }
}