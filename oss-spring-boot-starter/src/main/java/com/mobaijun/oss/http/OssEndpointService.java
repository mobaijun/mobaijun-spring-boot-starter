package com.mobaijun.oss.http;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.util.List;
import java.util.Map;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * InterfaceName: OssEndpointService
 * 接口描述： oss 对外提供服务的接口
 *
 * @author MoBaiJun 2022/5/6 15:36
 */
public interface OssEndpointService {

    /**
     * Bucket Endpoints
     *
     * @param bucketName bucketName
     * @return Bucket
     */
    Bucket createBucket(String bucketName);

    /**
     * getBuckets（）
     *
     * @return List<Bucket>
     */
    List<Bucket> getBuckets();

    /**
     * getBucket()
     *
     * @param bucketName bucketName
     * @return Bucket
     */
    Bucket getBucket(String bucketName);

    /**
     * deleteBucket
     *
     * @param bucketName bucketName
     */
    void deleteBucket(String bucketName);


    /**
     * filterObject()
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @return List<S3ObjectSummary>
     */
    List<S3ObjectSummary> filterObject(String bucketName,
                                       String objectName);

    /**
     * getObjectUrl()
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param expires    expires
     * @return Map<String, Object>
     */
    Map<String, Object> getObjectUrl(String bucketName,
                                     String objectName,
                                     Integer expires);

    /**
     * getPutObjectUrl
     *
     * @param bucketName bucketName
     * @param objectName objectName
     * @param expires    expires
     * @return Map<String, Object>
     */
    Map<String, Object> getPutObjectUrl(String bucketName,
                                        String objectName, Integer expires);

    /**
     * deleteObject()
     *
     * @param bucketName bucketName
     * @param objectName objectName
     */
    void deleteObject(String bucketName, String objectName);
}
