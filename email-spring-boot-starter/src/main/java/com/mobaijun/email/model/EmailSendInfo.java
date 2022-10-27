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
package com.mobaijun.email.model;

import java.time.LocalDateTime;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: EmailSendInfo
 * class description： 邮件发送详情
 *
 * @author MoBaiJun 2022/8/24 8:45
 */
public class EmailSendInfo {

    /**
     * 邮件信息
     */
    private final EmailDetails emailDetails;

    /**
     * 发送时间
     */
    private LocalDateTime sentDate;

    /**
     * 是否发送成功
     */
    private Boolean success;

    /**
     * 错误信息 errorMsg
     */
    private String errorMsg;

    public EmailSendInfo(EmailDetails emailDetails) {
        this.emailDetails = emailDetails;
    }

    public EmailSendInfo(EmailDetails emailDetails, LocalDateTime sentDate, Boolean success, String errorMsg) {
        this.emailDetails = emailDetails;
        this.sentDate = sentDate;
        this.success = success;
        this.errorMsg = errorMsg;
    }

    public EmailDetails getEmailDetails() {
        return emailDetails;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "EmailSendInfo{" +
                "emailDetails=" + emailDetails +
                ", sentDate=" + sentDate +
                ", success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}