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

import java.io.File;
import java.util.Arrays;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: MailDetails
 * class description：邮件详情
 *
 * @author MoBaiJun 2022/8/24 8:44
 */
public class EmailDetails {
    /**
     * 发件人
     */
    private String from;

    /**
     * 收件人
     */
    private String[] to;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 是否渲染html
     */
    private Boolean showHtml;

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 抄送
     */
    private String[] cc;

    /**
     * 密送
     */
    private String[] bcc;

    /**
     * 附件
     */
    private File[] files;

    public EmailDetails() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Boolean getShowHtml() {
        return showHtml;
    }

    public void setShowHtml(Boolean showHtml) {
        this.showHtml = showHtml;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public String[] getBcc() {
        return bcc;
    }

    public void setBcc(String[] bcc) {
        this.bcc = bcc;
    }

    public File[] getFiles() {
        return files;
    }

    public void setFiles(File[] files) {
        this.files = files;
    }

    public EmailDetails(String from, String[] to, String subject, Boolean showHtml, String content, String[] cc, String[] bcc, File[] files) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.showHtml = showHtml;
        this.content = content;
        this.cc = cc;
        this.bcc = bcc;
        this.files = files;
    }

    @Override
    public String toString() {
        return "EmailDetails{" +
                "from='" + from + '\'' +
                ", to=" + Arrays.toString(to) +
                ", subject='" + subject + '\'' +
                ", showHtml=" + showHtml +
                ", content='" + content + '\'' +
                ", cc=" + Arrays.toString(cc) +
                ", bcc=" + Arrays.toString(bcc) +
                ", files=" + Arrays.toString(files) +
                '}';
    }
}