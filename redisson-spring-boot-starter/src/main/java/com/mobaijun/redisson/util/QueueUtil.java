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
package com.mobaijun.redisson.util;

import com.mobaijun.core.spring.SpringUtil;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RBoundedBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RPriorityBlockingQueue;
import org.redisson.api.RedissonClient;

/**
 * Description:
 * <p>
 * 分布式队列工具
 * 轻量级队列 重量级数据量 请使用 MQ
 * 要求 redis 5.X 以上
 * <p>
 * Author: [mobaijun]
 * Date: [2024/12/27 11:19]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class QueueUtil {

    /**
     * 客户端实例
     */
    private static final RedissonClient CLIENT = SpringUtil.getBean(RedissonClient.class);


    /**
     * 获取客户端实例
     */
    public static RedissonClient getClient() {
        return CLIENT;
    }

    /**
     * 添加普通队列数据
     *
     * @param queueName 队列名
     * @param data      数据
     */
    public static <T> boolean addQueueObject(String queueName, T data) {
        RBlockingQueue<T> queue = CLIENT.getBlockingQueue(queueName);
        return queue.offer(data);
    }

    /**
     * 通用获取一个队列数据 没有数据返回 null(不支持延迟队列)
     *
     * @param queueName 队列名
     */
    public static <T> T getQueueObject(String queueName) {
        RBlockingQueue<T> queue = CLIENT.getBlockingQueue(queueName);
        return queue.poll();
    }

    /**
     * 通用删除队列数据(不支持延迟队列)
     */
    public static <T> boolean removeQueueObject(String queueName, T data) {
        RBlockingQueue<T> queue = CLIENT.getBlockingQueue(queueName);
        return queue.remove(data);
    }

    /**
     * 通用销毁队列 所有阻塞监听 报错(不支持延迟队列)
     */
    public static <T> boolean destroyQueue(String queueName) {
        RBlockingQueue<T> queue = CLIENT.getBlockingQueue(queueName);
        return queue.delete();
    }

    /**
     * 添加延迟队列数据 默认毫秒
     *
     * @param queueName 队列名
     * @param data      数据
     * @param time      延迟时间
     */
    public static <T> void addDelayedQueueObject(String queueName, T data, long time) {
        addDelayedQueueObject(queueName, data, time, TimeUnit.MILLISECONDS);
    }

    /**
     * 添加延迟队列数据
     *
     * @param queueName 队列名
     * @param data      数据
     * @param time      延迟时间
     * @param timeUnit  单位
     */
    public static <T> void addDelayedQueueObject(String queueName, T data, long time, TimeUnit timeUnit) {
        RBlockingQueue<T> queue = CLIENT.getBlockingQueue(queueName);
        RDelayedQueue<T> delayedQueue = CLIENT.getDelayedQueue(queue);
        delayedQueue.offer(data, time, timeUnit);
    }

    /**
     * 获取一个延迟队列数据 没有数据返回 null
     *
     * @param queueName 队列名
     */
    public static <T> T getDelayedQueueObject(String queueName) {
        RBlockingQueue<T> queue = CLIENT.getBlockingQueue(queueName);
        RDelayedQueue<T> delayedQueue = CLIENT.getDelayedQueue(queue);
        return delayedQueue.poll();
    }

    /**
     * 删除延迟队列数据
     */
    public static <T> boolean removeDelayedQueueObject(String queueName, T data) {
        RBlockingQueue<T> queue = CLIENT.getBlockingQueue(queueName);
        RDelayedQueue<T> delayedQueue = CLIENT.getDelayedQueue(queue);
        return delayedQueue.remove(data);
    }

    /**
     * 销毁延迟队列 所有阻塞监听 报错
     */
    public static <T> void destroyDelayedQueue(String queueName) {
        RBlockingQueue<T> queue = CLIENT.getBlockingQueue(queueName);
        RDelayedQueue<T> delayedQueue = CLIENT.getDelayedQueue(queue);
        delayedQueue.destroy();
    }

    /**
     * 添加优先队列数据
     *
     * @param queueName 队列名
     * @param data      数据
     */
    public static <T> boolean addPriorityQueueObject(String queueName, T data) {
        RPriorityBlockingQueue<T> priorityBlockingQueue = CLIENT.getPriorityBlockingQueue(queueName);
        return priorityBlockingQueue.offer(data);
    }

    /**
     * 优先队列获取一个队列数据 没有数据返回 null(不支持延迟队列)
     *
     * @param queueName 队列名
     */
    public static <T> T getPriorityQueueObject(String queueName) {
        RPriorityBlockingQueue<T> queue = CLIENT.getPriorityBlockingQueue(queueName);
        return queue.poll();
    }

    /**
     * 优先队列删除队列数据(不支持延迟队列)
     */
    public static <T> boolean removePriorityQueueObject(String queueName, T data) {
        RPriorityBlockingQueue<T> queue = CLIENT.getPriorityBlockingQueue(queueName);
        return queue.remove(data);
    }

    /**
     * 优先队列销毁队列 所有阻塞监听 报错(不支持延迟队列)
     */
    public static <T> boolean destroyPriorityQueue(String queueName) {
        RPriorityBlockingQueue<T> queue = CLIENT.getPriorityBlockingQueue(queueName);
        return queue.delete();
    }

    /**
     * 尝试设置 有界队列 容量 用于限制数量
     *
     * @param queueName 队列名
     * @param capacity  容量
     */
    public static <T> boolean trySetBoundedQueueCapacity(String queueName, int capacity) {
        RBoundedBlockingQueue<T> boundedBlockingQueue = CLIENT.getBoundedBlockingQueue(queueName);
        return boundedBlockingQueue.trySetCapacity(capacity);
    }

    /**
     * 尝试设置 有界队列 容量 用于限制数量
     *
     * @param queueName 队列名
     * @param capacity  容量
     * @param destroy   是否销毁
     */
    public static <T> boolean trySetBoundedQueueCapacity(String queueName, int capacity, boolean destroy) {
        RBoundedBlockingQueue<T> boundedBlockingQueue = CLIENT.getBoundedBlockingQueue(queueName);
        if (destroy) {
            boundedBlockingQueue.delete();
        }
        return boundedBlockingQueue.trySetCapacity(capacity);
    }

    /**
     * 添加有界队列数据
     *
     * @param queueName 队列名
     * @param data      数据
     * @return 添加成功 true 已达到界限 false
     */
    public static <T> boolean addBoundedQueueObject(String queueName, T data) {
        RBoundedBlockingQueue<T> boundedBlockingQueue = CLIENT.getBoundedBlockingQueue(queueName);
        return boundedBlockingQueue.offer(data);
    }

    /**
     * 有界队列获取一个队列数据 没有数据返回 null(不支持延迟队列)
     *
     * @param queueName 队列名
     */
    public static <T> T getBoundedQueueObject(String queueName) {
        RBoundedBlockingQueue<T> queue = CLIENT.getBoundedBlockingQueue(queueName);
        return queue.poll();
    }

    /**
     * 有界队列删除队列数据(不支持延迟队列)
     */
    public static <T> boolean removeBoundedQueueObject(String queueName, T data) {
        RBoundedBlockingQueue<T> queue = CLIENT.getBoundedBlockingQueue(queueName);
        return queue.remove(data);
    }

    /**
     * 有界队列销毁队列 所有阻塞监听 报错(不支持延迟队列)
     */
    public static <T> boolean destroyBoundedQueue(String queueName) {
        RBoundedBlockingQueue<T> queue = CLIENT.getBoundedBlockingQueue(queueName);
        return queue.delete();
    }

    /**
     * 订阅阻塞队列(可订阅所有实现类 例如: 延迟 优先 有界 等)
     */
    public static <T> void subscribeBlockingQueue(String queueName, Function<T, CompletionStage<Void>> consumer, boolean isDelayed) {
        RBlockingQueue<T> queue = CLIENT.getBlockingQueue(queueName);
        if (isDelayed) {
            // 订阅延迟队列
            CLIENT.getDelayedQueue(queue);
        }
        queue.subscribeOnElements(consumer);
    }
}