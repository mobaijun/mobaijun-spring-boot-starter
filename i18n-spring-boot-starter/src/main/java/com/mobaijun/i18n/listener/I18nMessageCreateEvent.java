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
package com.mobaijun.i18n.listener;

import com.mobaijun.i18n.model.I18nMessage;
import java.util.List;
import org.springframework.context.ApplicationEvent;

/**
 * Description: I18nMessage 的创建事件，Listener 监听此事件，进行 I18nMessage 的存储
 * Author: [mobaijun]
 * Date: [2024/8/15 12:00]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class I18nMessageCreateEvent extends ApplicationEvent {

    /**
     * 构造方法
     *
     * @param i18nMessages 待创建的 I18nMessage 列表
     */
    public I18nMessageCreateEvent(List<I18nMessage> i18nMessages) {
        super(i18nMessages);
    }

    /**
     * 获取待创建的 I18nMessage 列表
     *
     * @return 待创建的 I18nMessage 列表
     */
    @SuppressWarnings("unchecked")
    public List<I18nMessage> getI18nMessages() {
        return (List<I18nMessage>) super.getSource();
    }
}
