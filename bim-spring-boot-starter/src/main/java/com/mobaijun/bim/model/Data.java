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
package com.mobaijun.bim.model;

import java.time.LocalDateTime;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: Data
 * class description：
 *
 * @author MoBaiJun 2022/10/19 8:42
 */
public class Data {

    /**
     * 主机地址
     */
    private String host;

    /**
     * 策略
     */
    private String policy;

    /**
     * 访问标识
     */
    private String accessId;

    /**
     * 签名
     */
    private String signature;

    /**
     * 过期时间
     */
    private long expire;

    /**
     * 回调数据
     */
    private String callbackBody;

    /**
     * 对象键
     */
    private String objectKey;

    /**
     * 源标识
     */
    private String sourceId;

    /**
     * 唯一标识
     */
    private String id;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 文件标识
     */
    private String etag;

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 文件长度
     */
    private int length;

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件状态
     */
    private String status;

    /**
     * 文件后缀
     */
    private String suffix;

    /**
     * 应用标识
     */
    private String appKey;

    /**
     * 是否压缩
     */
    private boolean compressed;

    /**
     * 花费
     */
    private String cost;

    /**
     * 数据包标识
     */
    private String databagId;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 预估花费
     */
    private String estimatedCost;

    /**
     * 输出格式
     */
    private String outputFormat;

    /**
     * 优先级
     */
    private String priority;

    /**
     * 项目标识
     */
    private String projectId;

    /**
     * 原因
     */
    private String reason;

    /**
     * 根名称
     */
    private String rootName;

    /**
     * 缩略图
     */
    private String thumbnail;

    /**
     * 文件项标识
     */
    private String fileItemId;

    /**
     * 父标识
     */
    private String parentId;

    /**
     * 文件夹
     */
    private String folder;

    /**
     * 存储标识
     */
    private String storeId;

    /**
     * 版本
     */
    private String version;

    /**
     * 物理索引
     */
    private String physicalIndex;

    /**
     * 状态码
     */
    private String code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private String data;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Data{" +
                "host='" + host + '\'' +
                ", policy='" + policy + '\'' +
                ", accessId='" + accessId + '\'' +
                ", signature='" + signature + '\'' +
                ", expire=" + expire +
                ", callbackBody='" + callbackBody + '\'' +
                ", objectKey='" + objectKey + '\'' +
                ", sourceId='" + sourceId + '\'' +
                ", id='" + id + '\'' +
                ", createTime=" + createTime +
                ", etag='" + etag + '\'' +
                ", fileId='" + fileId + '\'' +
                ", length=" + length +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", suffix='" + suffix + '\'' +
                ", appKey='" + appKey + '\'' +
                ", compressed=" + compressed +
                ", cost='" + cost + '\'' +
                ", databagId='" + databagId + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", estimatedCost='" + estimatedCost + '\'' +
                ", outputFormat='" + outputFormat + '\'' +
                ", priority='" + priority + '\'' +
                ", projectId='" + projectId + '\'' +
                ", reason='" + reason + '\'' +
                ", rootName='" + rootName + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", fileItemId='" + fileItemId + '\'' +
                ", parentId='" + parentId + '\'' +
                ", folder='" + folder + '\'' +
                ", storeId='" + storeId + '\'' +
                ", version='" + version + '\'' +
                ", physicalIndex='" + physicalIndex + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public String getCallbackBody() {
        return callbackBody;
    }

    public void setCallbackBody(String callbackBody) {
        this.callbackBody = callbackBody;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public boolean isCompressed() {
        return compressed;
    }

    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDatabagId() {
        return databagId;
    }

    public void setDatabagId(String databagId) {
        this.databagId = databagId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(String estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getFileItemId() {
        return fileItemId;
    }

    public void setFileItemId(String fileItemId) {
        this.fileItemId = fileItemId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPhysicalIndex() {
        return physicalIndex;
    }

    public void setPhysicalIndex(String physicalIndex) {
        this.physicalIndex = physicalIndex;
    }
}