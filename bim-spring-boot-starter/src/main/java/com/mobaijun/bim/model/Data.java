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
    private String host;
    private String policy;
    private String accessId;
    private String signature;
    private long expire;
    private String callbackBody;
    private String objectKey;
    private String sourceId;

    private String id;
    private LocalDateTime createTime;
    private String etag;
    private String fileId;
    private int length;
    private String name;
    private String status;
    private String suffix;

    private String appKey;
    private boolean compressed;
    private String cost;
    private String databagId;
    private String errorCode;
    private String estimatedCost;
    private String outputFormat;
    private String priority;
    private String projectId;
    private String reason;
    private String rootName;
    private String thumbnail;
    private String fileItemId;
    private String parentId;
    private String folder;
    private String storeId;
    private String version;
    private String physicalIndex;

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
