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
package com.mobaijun.jpush.util;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.connection.HttpProxy;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.utils.Preconditions;
import cn.jiguang.common.utils.StringUtils;
import cn.jpush.api.JPushClient;
import cn.jpush.api.device.AliasDeviceListResult;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.schedule.ScheduleResult;
import com.mobaijun.jpush.model.PushMessage;
import com.mobaijun.jpush.prop.PushProp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: PushUtils
 * class description： 极光推送工具类
 *
 * @author MoBaiJun 2022/6/21 10:31
 */
public class PushUtil {

    /**
     * logger
     */
    private static final Logger log = LoggerFactory.getLogger(PushUtil.class);

    /**
     * 极光推送客户端工具类
     */
    private JPushClient pushClient;

    /**
     * 极光推送配置文件
     */
    private final PushProp pushProp;

    /**
     * 初始化加载配置
     *
     * @param pushProp 极光配置文件
     */
    public PushUtil(PushProp pushProp) {
        this.pushProp = pushProp;
        initialization();
    }

    /**
     * 极光推送初始化配置
     */
    private void initialization() {
        Preconditions.checkArgument(StringUtils.isNotEmpty(pushProp.getAppKey()), "appKey is empty");
        Preconditions.checkArgument(StringUtils.isNotEmpty(pushProp.getMasterSecret()), "masterSecret is empty");
        HttpProxy proxy = null;
        if (pushProp.isUseProxy()) {
            Preconditions.checkArgument(StringUtils.isNotEmpty(pushProp.getProxyHost()), "Proxy server hostname or IP is empty");
            Preconditions.checkArgument(pushProp.getProxyPort() > 1, "Invalid proxy server host port");
            if (StringUtils.isNotEmpty(pushProp.getProxyUsername())) {
                // 代理有效，新建代理
                proxy = new HttpProxy(pushProp.getProxyHost(), pushProp.getProxyPort()
                        , pushProp.getProxyUsername(), pushProp.getProxyPassword());
            } else {
                // 代理有效，新建代理
                proxy = new HttpProxy(pushProp.getProxyHost(), pushProp.getProxyPort());
            }
        }
        // 新建连接
        pushClient = new JPushClient(pushProp.getMasterSecret(), pushProp.getAppKey(), proxy, ClientConfig.getInstance());
        log.info("=================== Initialization of the aurora reckoning interface succeeded! ===================");
    }

    /**
     * 根据设备ID推送
     *
     * @param registrationIdList 设备Registration ID列表
     * @param pm                 消息对象
     * @return 成功时返回消息ID
     */
    public Long pushToDevices(List<String> registrationIdList, PushMessage pm) {
        // 参数检查
        checkArgument(registrationIdList);
        return push(createPushPayload(pm, Audience.registrationId(registrationIdList)));
    }

    /**
     * 根据别名推送
     *
     * @param aliasList 别名列表
     * @param pm        消息
     * @return 成功时返回消息ID
     */
    public Long pushToAliases(List<String> aliasList, PushMessage pm) {
        // 参数检查
        checkArgument(aliasList);
        return push(createPushPayload(pm, Audience.alias(aliasList)));
    }

    /**
     * 根据标签推送
     *
     * @param tagsList 标签列表
     * @param pm       消息
     * @return 成功时返回消息ID
     */
    public Long pushToTags(List<String> tagsList, PushMessage pm) {
        // 参数检查
        checkArgument(tagsList);
        return push(createPushPayload(pm, Audience.tag(tagsList)));
    }

    /**
     * 推送给所有客户端
     *
     * @param pm 消息
     * @return 成功时返回消息ID
     */
    public Long pushToAll(PushMessage pm) {
        return push(createPushPayload(pm, Audience.all()));
    }

    /**
     * 定时发送全部用户
     *
     * @param pm   消息体
     * @param time 定时时间
     * @return 是否成功
     */
    public boolean pushToSchedule(PushMessage pm, LocalDateTime time) throws APIConnectionException, APIRequestException {
        return sendToSchedule(pm, time, Audience.all());
    }

    /**
     * LocalDateTime转换为 yyyy-MM-dd HH:mm:ss 格式
     *
     * @param localDateTime 转换日期
     * @return 格式化日期字符串
     */
    private String localDateTimeFormat(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 定时发送消息
     *
     * @param pm       消息对象
     * @param time     发送时间 格式:yyyy-MM-dd HH:mm:ss
     * @param audience 发送对象
     * @return true 成功 false 失败
     */
    public boolean sendToSchedule(PushMessage pm, LocalDateTime time, Audience audience) throws APIConnectionException, APIRequestException {
        ScheduleResult schedule = pushClient.createSingleSchedule(pm.getContent(),
                // 定时发送时间,格式化后
                localDateTimeFormat(time),
                // 指定平台进行推送
                createPushPayload(pm, audience),
                // MasterSecret
                pushProp.getMasterSecret(),
                // AppKey
                pushProp.getAppKey());
        Optional.of(schedule).map(temp -> temp.getResponseCode() == HttpStatus.SC_OK);
        return true;
    }

    /**
     * 查询指定别名下的设备Registration ID
     *
     * @param alias 待查询的别名
     * @return 关联的设备Registration ID列表
     */
    public List<String> findRegistrationId(String alias) {
        if (StringUtils.isEmpty(alias)) {
            return null;
        }
        try {
            AliasDeviceListResult result = pushClient.getAliasDeviceList(alias, null);
            if (result == null || HttpStatus.SC_OK != result.getResponseCode()) {
                return null;
            }
            return result.registration_ids;
        } catch (APIConnectionException | APIRequestException e) {
            log.error("An exception occurred while querying the device associated with the alias! alias:{}", alias, e);
        }
        return null;
    }

    /**
     * 删除别名
     *
     * @param aliasList 待删除的别名列表
     */
    public void deleteAlias(List<String> aliasList) {
        if (CollectionUtils.isEmpty(aliasList)) {
            return;
        }
        aliasList.forEach(a -> {
            if (StringUtils.isEmpty(a)) {
                return;
            }
            try {
                pushClient.deleteAlias(a, null);
            } catch (APIConnectionException | APIRequestException e) {
                log.error("An exception occurred while deleting the alias! alias:{}", a, e);
            }
        });
    }

    /**
     * 参数检查
     *
     * @param aliasList 别名列表 || 设备Registration ID列表
     */
    private void checkArgument(List<String> aliasList) {
        Preconditions.checkArgument(!CollectionUtils.isEmpty(aliasList), "Device Registration ID is empty");
        Preconditions.checkArgument(aliasList.size() <= 1000, "Device Registration ID no more than 1000");
    }

    /**
     * 进行推送(支持重试)
     *
     * @param payload 消息体
     * @return 成功时返回消息ID
     */
    @SuppressWarnings("BusyWait")
    private Long pushWithRetry(PushPayload payload) throws Exception {
        Long msgId = executePush(payload);
        if (msgId == null) {
            long sleepTime = pushProp.getRetryInterval() > 0 ? pushProp.getRetryInterval() : 500L;
            for (int i = 0; i < pushProp.getRetryMaxAttempts(); i++) {
                Thread.sleep(sleepTime);
                msgId = executePush(payload);
                if (msgId != null) {
                    break;
                }
                sleepTime *= 2;
            }
        }
        return msgId;
    }

    /**
     * 执行推送
     *
     * @param payload 消息体
     * @return 成功时返回消息ID
     */
    private Long executePush(PushPayload payload) {
        log.warn("push message body: {}", payload.toJSON());
        try {
            PushResult result = pushClient.sendPush(payload);
            if (result == null) {
                return null;
            }
            if (HttpStatus.SC_OK == result.getResponseCode()) {
                log.warn("The message is pushed successfully, the message ID: {}", result.msg_id);
                return result.msg_id;
            }
            log.error("Message push failed, ResponseCode={}", result.getResponseCode());
        } catch (APIConnectionException e) {
            log.error("APIConnectionException in push message", e);
        } catch (APIRequestException e) {
            log.error("An APIRequestException occurs in the push message", e);
            log.error("The push message is abnormal, status={}, errorCode={}, errorMessage={}",
                    e.getStatus(), e.getErrorCode(), e.getErrorMessage());
        }
        return null;
    }

    /**
     * 进行推送
     *
     * @param payload 消息体
     * @return 成功时返回消息ID
     */
    private Long push(PushPayload payload) {
        if (pushProp.getRetryMaxAttempts() != null && pushProp.getRetryMaxAttempts() > 0) {
            try {
                return pushWithRetry(payload);
            } catch (Exception e) {
                log.error("An exception occurred while pushing", e);
            }
            return null;
        } else {
            return executePush(payload);
        }
    }

    /**
     * 指定平台进行推送
     *
     * @param pm       消息内容
     * @param audience 受众群体
     * @return 推送载荷配置
     */
    private PushPayload createPushPayload(PushMessage pm, Audience audience) {
        // 判断消息内容是否为 null
        Preconditions.checkArgument(pm != null, "message is empty ！！ ");
        Preconditions.checkArgument(StringUtils.isNotEmpty(pm.getContent()), "message content is empty！！！");
        // 消息推送生成器
        return PushPayload.newBuilder()
                // 指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.all())
                // 指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                // 注意，该用户必须存在于该appKey拥有者的服务用户下，不然会提示找不到该用户
                .setAudience(audience)
                // 推送设置
                .setOptions(Options.newBuilder()
                        // 此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(pushProp.isApnsProduction())
                        // 此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
                        // .setTimeToLive()
                        .build())
                // jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，WinPhone的由mpns下发
                .setNotification(Notification.newBuilder()
                        // 传一个IosAlert对象，指定apns title、title、subtitle
                        .setAlert(pm.getContent())
                        .addPlatformNotification(IosNotification.newBuilder()
                                // 此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                .setSound("default")
                                // 支持 setBadge(int) 方法来设置；支持 incrBadge(int) 方法来增加。
                                .setBadge(pm.getBadge() != null ? pm.getBadge() : 1)
                                // 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtras(pm.getExtras()).build())
                        // 添加平台通知
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setBuilderId(1)
                                .setPriority(pm.getPriority() != null ? pm.getPriority() : 0)
                                // 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtras(pm.getExtras())
                                .build())
                        .build()
                )
                // Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder()
                        .setMsgContent(pm.getContent())
                        .setTitle(pm.getTitle() != null ? pm.getTitle() : "")
                        .build())
                .build();
    }
}