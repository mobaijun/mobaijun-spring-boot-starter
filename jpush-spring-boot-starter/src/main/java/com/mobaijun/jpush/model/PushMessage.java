package com.mobaijun.jpush.model;

import java.util.Map;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: PushMessage
 * class description： 推送消息内容
 *
 * @author MoBaiJun 2022/6/21 10:51
 */
public class PushMessage {

    /**
     * 消息内容
     */
    private String content;

    /**
     * 角标
     */
    private Integer badge;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 附加业务参数
     */
    private Map<String, String> extras;

    /**
     * 获取 消息内容
     *
     * @return content 消息内容
     */
    public String getContent() {
        return this.content.trim();
    }

    /**
     * 设置 消息内容
     *
     * @param content 消息内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取 优先级
     *
     * @return priority 优先级
     */
    public Integer getPriority() {
        return this.priority;
    }

    /**
     * 设置 优先级
     *
     * @param priority 优先级
     */
    public void setPriority(Integer priority) {
        if (priority != null) {
            if (priority < -2 || priority > 2) {
                throw new IllegalArgumentException("优先级的取值范围为 -2～2 之间");
            }
        }
        this.priority = priority;
    }

    /**
     * 获取 角标
     *
     * @return badge 角标
     */
    public Integer getBadge() {
        return this.badge;
    }

    /**
     * 设置 角标
     *
     * @param badge 角标
     */
    public void setBadge(Integer badge) {
        this.badge = badge;
    }

    /**
     * 获取 附加业务参数
     *
     * @return extras 附加业务参数
     */
    public Map<String, String> getExtras() {
        return this.extras;
    }

    /**
     * 设置 附加业务参数
     *
     * @param extras 附加业务参数
     */
    public void setExtras(Map<String, String> extras) {
        this.extras = extras;
    }
}
