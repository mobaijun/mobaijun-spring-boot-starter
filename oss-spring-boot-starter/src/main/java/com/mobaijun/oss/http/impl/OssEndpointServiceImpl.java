package com.mobaijun.oss.http.impl;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.mobaijun.oss.http.OssEndpointService;
import com.mobaijun.oss.service.OssTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: OssEndpointServiceImpl
 * 类描述： 实现
 *
 * @author MoBaiJun 2022/5/6 15:37
 */
public class OssEndpointServiceImpl implements OssEndpointService {

    private static Logger log = LoggerFactory.getLogger(OssEndpointServiceImpl.class);

    private OssTemplate ossTemplate;

    public OssEndpointServiceImpl(OssTemplate template) {
        log.info("============= OssEndpointServiceImpl build successfully =============");
        this.ossTemplate = template;
    }

    @Override
    public Bucket createBucket(String bucketName) {
        ossTemplate.createBucket(bucketName);
        return ossTemplate.getBucket(bucketName).get();
    }

    @Override
    public List<Bucket> getBuckets() {
        return ossTemplate.getAllBuckets();
    }

    @Override
    public Bucket getBucket(String bucketName) {
        return ossTemplate.getBucket(bucketName).orElseThrow(() -> new IllegalArgumentException("Bucket Name not found!"));
    }

    @Override
    public void deleteBucket(String bucketName) {
        ossTemplate.removeBucket(bucketName);
    }

    @Override
    public List<S3ObjectSummary> filterObject(String bucketName, String objectName) {
        return ossTemplate.getAllObjectsByPrefix(bucketName, objectName);
    }

    @Override
    public Map<String, Object> getObjectUrl(String bucketName, String objectName, Integer expires) {
        // Put Object info
        return responseBody(bucketName, objectName, expires);
    }

    @Override
    public Map<String, Object> getPutObjectUrl(String bucketName, String objectName, Integer expires) {
        return responseBody(bucketName, objectName, expires);
    }

    @Override
    public void deleteObject(String bucketName, String objectName) {

    }

    private Map<String, Object> responseBody(String bucketName, String objectName, Integer expires) {
        Map<String, Object> responseBody = new HashMap<>(8);
        responseBody.put("bucket", bucketName);
        responseBody.put("object", objectName);
        responseBody.put("url", ossTemplate.getPutObjectURL(bucketName, objectName, expires));
        responseBody.put("expires", expires);
        return responseBody;
    }
}
