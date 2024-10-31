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
package com.mobaijun.minio.util;

import com.mobaijun.minio.exception.MinioException;
import com.mobaijun.minio.exception.MinioFetchException;
import com.mobaijun.minio.prop.MinioConfigurationProperties;
import com.sun.java.accessibility.util.EventID;
import io.minio.BucketExistsArgs;
import io.minio.DownloadObjectArgs;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListObjectsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveBucketArgs;
import io.minio.RemoveObjectArgs;
import io.minio.RemoveObjectsArgs;
import io.minio.Result;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.UploadObjectArgs;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.util.CollectionUtils;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: MinioService
 * class description： 服务类，用于与 Minio 存储桶进行交互。
 * 该类作为 Bean 注册，并使用 {@link MinioConfigurationProperties} 中定义的属性。
 * 所有方法都返回 {@link MinioException}，该异常封装了 Minio SDK 的异常。
 * 存储桶名称由配置属性中定义的名称提供。
 *
 * @author MoBaiJun 2022/9/19 17:37
 */
public class MinioService {

    /**
     * Minio Client
     */
    private final MinioClient minioClient;

    /**
     * 配置文件属性
     */
    private final MinioConfigurationProperties minioConfigurationProperties;

    /**
     * 初始化
     *
     * @param minioClient     minio 客户端
     * @param minioProperties 配置文件属性
     */
    public MinioService(MinioClient minioClient, MinioConfigurationProperties minioProperties) {
        this.minioClient = minioClient;
        this.minioConfigurationProperties = minioProperties;
    }

    /**
     * 获取 MinIO 客户端实例。
     *
     * @return MinioClient 实例，用于与 MinIO 服务进行交互。
     * 如果尚未初始化，可能会抛出相关异常。
     */
    public MinioClient getMinioClient() {
        return minioClient;
    }

    /**
     * 检查存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @return 存储桶是否存在
     * @throws Exception 如果检查过程中发生错误
     */
    public boolean existsBucket(String bucketName) throws Exception {
        return minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(bucketName)
                .build()
        );
    }

    /**
     * 创建一个存储桶
     *
     * @param bucketName 存储桶名称
     * @throws Exception 如果创建存储桶过程中发生错误
     */
    public void createBucket(String bucketName) throws Exception {
        if (!existsBucket(bucketName)) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    /**
     * 获取所有存储桶的信息列表
     *
     * @return 存储桶列表
     * @throws Exception 如果获取存储桶列表过程中发生错误
     */
    public List<Bucket> getAllBuckets() throws Exception {
        return minioClient.listBuckets();
    }

    /**
     * 获取所有存储桶的名称列表
     *
     * @return 存储桶名称列表
     * @throws Exception 如果获取存储桶名称列表过程中发生错误
     */
    public List<String> getAllBucketNames() throws Exception {
        List<Bucket> bucketList = getAllBuckets();
        return !CollectionUtils.isEmpty(bucketList) ?
                bucketList.stream().map(Bucket::name)
                        .toList() : new ArrayList<>();
    }

    /**
     * 列出默认存储桶根目录下的所有对象
     *
     * @return 对象列表
     */
    public List<Item> listObjects() {
        return listObjects(null);
    }

    /**
     * 列出指定存储桶根目录下的所有对象，如果未指定存储桶，则使用默认存储桶
     *
     * @param bucket 存储桶名称，如果为空，则使用默认存储桶
     * @return 对象列表
     */
    public List<Item> listObjects(String bucket) {
        bucket = (bucket == null || bucket.isEmpty()) ? minioConfigurationProperties.getBucket() : bucket;
        ListObjectsArgs args = ListObjectsArgs.builder()
                .bucket(bucket)
                // 列出根目录下的对象
                .prefix("")
                // 不递归列出子目录
                .recursive(false)
                .build();
        return getItems(minioClient.listObjects(args));
    }

    /**
     * 删除一个存储桶
     *
     * @param bucketName 存储桶名称
     * @return 删除是否成功
     * @throws Exception 如果删除存储桶过程中发生错误
     */
    public boolean deleteBucket(String bucketName) throws Exception {
        boolean flag = existsBucket(bucketName);
        if (flag) {
            Iterable<Result<Item>> myObjects = getListObjects(bucketName);
            for (Result<Item> result : myObjects) {
                Item item = result.get();
                // 如果存储桶中存在对象文件，则删除失败
                if (item.size() > 0) {
                    return false;
                }
            }
            // 只有当存储桶为空时才能删除存储桶
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
            flag = existsBucket(bucketName);
            return !flag;
        }
        return false;
    }

    /**
     * 列出存储桶中的所有对象名称
     *
     * @param bucketName 存储桶名称
     * @return 对象名称列表
     * @throws Exception 如果列出对象名称过程中发生错误
     */
    public List<String> listObjectNames(String bucketName) throws Exception {
        List<String> listObjectNames = new ArrayList<>();
        if (existsBucket(bucketName)) {
            Iterable<Result<Item>> myObjects = getListObjects(bucketName);
            for (Result<Item> result : myObjects) {
                Item item = result.get();
                listObjectNames.add(item.objectName());
            }
        }
        return listObjectNames;
    }

    /**
     * 列出存储桶中的所有对象
     *
     * @param bucketName 存储桶名称
     * @return 对象结果的迭代器
     * @throws Exception 如果列出对象过程中发生错误
     */
    public Iterable<Result<Item>> getListObjects(String bucketName) throws Exception {
        if (existsBucket(bucketName)) {
            return minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
        }
        return null;
    }

    /**
     * 获取文件列表
     *
     * @return 文件列表
     */
    public List<String> getListFiles() {
        // 获取存储桶中的文件对象列表
        return !CollectionUtils.isEmpty(listObjects()) ? listObjects().stream()
                .map(Item::objectName)
                .toList() : new LinkedList<>();
    }

    /**
     * 获取文件的 URL
     *
     * @param fileName 存储桶中文件的相对路径
     * @return 文件的临时 URL
     * @throws MinioException 如果获取临时 URL 时发生错误
     */
    public String getFileUrl(String fileName) throws MinioException {
        return getFileUrl(fileName, null);
    }

    /**
     * 获取文件的 URL
     *
     * @param fileName 存储桶中文件的相对路径
     * @param bucket   存储桶名称。如果为 null 或空，则使用默认存储桶
     * @return 文件的临时 URL
     * @throws MinioException 如果获取临时 URL 时发生错误
     */
    public String getFileUrl(String fileName, String bucket) throws MinioException {
        // 使用默认存储桶名称，如果未提供
        String resolvedBucket = (bucket == null || bucket.isEmpty())
                ? minioConfigurationProperties.getBucket()
                : bucket;

        // 设置获取临时 URL 的参数
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .bucket(resolvedBucket)
                .object(fileName)
                .method(Method.GET)
                .expiry(Math.toIntExact(minioConfigurationProperties.getExpire().getSeconds()), TimeUnit.SECONDS)
                .build();

        try {
            // 返回文件的临时 URL
            return minioClient.getPresignedObjectUrl(args);
        } catch (Exception e) {
            // 抛出自定义异常，包含详细错误信息
            throw new MinioException("Error getting temporary file URL", e);
        }
    }

    /**
     * 列出默认存储桶根目录下的所有对象
     *
     * @return 对象列表
     */
    public List<Item> fullAllList() {
        return fullAllList(null);
    }

    /**
     * 列出指定存储桶根目录下的所有对象
     *
     * @param bucket 存储桶名称。如果为 null 或空，则使用默认存储桶
     * @return 对象列表
     */
    public List<Item> fullAllList(String bucket) {
        // 使用默认存储桶名称，如果未提供
        String resolvedBucket = (bucket == null || bucket.isEmpty())
                ? minioConfigurationProperties.getBucket()
                : bucket;

        // 设置列出对象的参数
        ListObjectsArgs args = ListObjectsArgs.builder()
                .bucket(resolvedBucket)
                .build();

        // 获取对象列表并返回
        return getItems(minioClient.listObjects(args));
    }

    /**
     * 列出具有指定前缀的所有对象，模拟文件夹层级结构。
     * 不返回文件夹中的对象（即所有匹配模式 {@code {prefix}/{objectName}/...} 的对象）
     *
     * @param path 查找对象的前缀
     * @return 对象列表
     */
    public List<Item> listPrefix(Path path) {
        return listPrefix(path, null);
    }

    /**
     * 列出具有指定前缀的所有对象，模拟文件夹层级结构。
     * 不返回文件夹中的对象（即所有匹配模式 {@code {prefix}/{objectName}/...} 的对象）
     *
     * @param path 查找对象的前缀
     * @return 对象列表
     */
    public List<Item> listPrefix(Path path, String bucket) {
        bucket = (bucket == null || bucket.isEmpty()) ? minioConfigurationProperties.getBucket() : bucket;
        ListObjectsArgs args = ListObjectsArgs.builder()
                .bucket(bucket)
                .prefix(path.toString())
                .recursive(false)
                .build();
        Iterable<Result<Item>> myObjects = minioClient.listObjects(args);
        return getItems(myObjects);
    }

    /**
     * 列出存储桶中具有指定前缀的所有对象
     * <p>
     * 返回所有对象，包括那些在子目录中的对象。
     *
     * @param prefix 要查找的对象的前缀
     * @return 对象列表
     */
    public List<Item> fullList(Path prefix) {
        return fullList(prefix, null);
    }

    /**
     * 列出存储桶中具有指定前缀的所有对象
     * <p>
     * 返回所有对象，包括那些在子目录中的对象。
     *
     * @param prefix 要查找的对象的前缀
     * @param bucket 存储桶名称。如果为 null 或空，则使用默认存储桶
     * @return 对象列表
     */
    public List<Item> fullList(Path prefix, String bucket) {
        // 使用默认存储桶名称，如果未提供
        String resolvedBucket = (bucket == null || bucket.isEmpty())
                ? minioConfigurationProperties.getBucket()
                : bucket;

        // 设置列出对象的参数
        ListObjectsArgs args = ListObjectsArgs.builder()
                .bucket(resolvedBucket)
                .prefix(prefix.toString())
                .build();

        // 获取对象列表并返回
        Iterable<Result<Item>> objectResults = minioClient.listObjects(args);
        return getItems(objectResults);
    }

    /**
     * 工具方法，将结果映射为对象并返回列表
     *
     * @param myObjects 结果的可迭代集合
     * @return 对象列表
     */
    private List<Item> getItems(Iterable<Result<Item>> myObjects) {
        return StreamSupport
                .stream(myObjects.spliterator(), true)
                .map(itemResult -> {
                    try {
                        return itemResult.get();
                    } catch (Exception e) {
                        throw new MinioFetchException("Error while parsing list of objects", e);
                    }
                }).toList();
    }

    /**
     * 从 Minio 获取对象
     *
     * @param path 带前缀的对象路径。必须包括对象名称。
     * @return 作为 InputStream 的对象
     * @throws MinioException 如果获取对象时发生错误
     */
    public InputStream get(Path path) throws MinioException {
        try {
            GetObjectArgs args = GetObjectArgs.builder()
                    .bucket(minioConfigurationProperties.getBucket())
                    .object(path.toString())
                    .build();
            return minioClient.getObject(args);
        } catch (Exception e) {
            throw new MinioException("Error while fetching files in Minio", e);
        }
    }

    /**
     * 获取 Minio 中对象的元数据
     *
     * @param path 带前缀的对象路径。必须包括对象名称。
     * @return 对象的元数据
     * @throws MinioException 如果获取对象元数据时发生错误
     */
    public StatObjectResponse getMetadata(Path path) throws MinioException {
        try {
            StatObjectArgs args = StatObjectArgs.builder()
                    .bucket(minioConfigurationProperties.getBucket())
                    .object(path.toString())
                    .build();
            return minioClient.statObject(args);
        } catch (Exception e) {
            throw new MinioException("Error while fetching files in Minio", e);
        }
    }

    /**
     * 获取 Minio 中多个对象的元数据
     *
     * @param paths 所有对象的路径，必须包括对象名称。
     * @return 一个映射，其中所有路径是键，元数据是值
     */
    public Map<Path, StatObjectResponse> getMetadata(Iterable<Path> paths) {
        return StreamSupport.stream(paths.spliterator(), false)
                .map(path -> {
                    try {
                        StatObjectArgs args = StatObjectArgs.builder()
                                .bucket(minioConfigurationProperties.getBucket())
                                .object(path.toString())
                                .build();
                        return new HashMap.SimpleEntry<>(path, minioClient.statObject(args));
                    } catch (Exception e) {
                        throw new MinioFetchException("Error while parsing list of objects", e);
                    }
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * 从 Minio 获取一个文件，并将其保存到 {@code fileName} 文件中
     *
     * @param source   带前缀的对象路径。必须包括对象名称。
     * @param fileName 文件名
     * @throws MinioException 如果获取对象时发生错误
     */
    public void getAndSave(Path source, String fileName) throws MinioException {
        try {
            DownloadObjectArgs args = DownloadObjectArgs.builder()
                    .bucket(minioConfigurationProperties.getBucket())
                    .object(source.toString())
                    .filename(fileName)
                    .build();
            minioClient.downloadObject(args);
        } catch (Exception e) {
            throw new MinioException("Error while fetching files in Minio", e);
        }
    }

    /**
     * 上传文件到 Minio 并返回链接
     * 处理大于 JVM 最大内存的文件
     *
     * @param source      带前缀的对象路径。必须包括对象名称。
     * @param file        作为输入流的文件
     * @param contentType 请求文件的 MIME 类型
     * @return 文件的 URL
     * @throws MinioException 如果上传对象时发生错误
     */
    public String upload(InputStream file, Path source, String contentType) throws MinioException {
        return upload(file, source, contentType, null);
    }

    /**
     * 上传文件到 Minio 并返回链接
     * 处理大于 JVM 最大内存的文件
     *
     * @param source      带前缀的对象路径。必须包括对象名称。
     * @param file        作为输入流的文件
     * @param contentType 请求文件的 MIME 类型
     * @param bucket      存储桶名称。若为空，则使用配置中的默认桶。
     * @return 文件的 URL
     * @throws MinioException 如果上传对象时发生错误
     */
    public String upload(InputStream file, Path source, String contentType, String bucket) throws MinioException {
        String fileUrl;
        try {
            // 如果 bucket 参数为空，则使用默认配置中的 bucket 名称
            bucket = (bucket == null || bucket.isEmpty()) ? minioConfigurationProperties.getBucket() : bucket;

            // 创建上传对象的参数
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(source.toString())
                    .stream(file, file.available(), -1)
                    .contentType(contentType)
                    .build();

            // 执行文件上传
            minioClient.putObject(args);

            // 获取文件的 URL
            fileUrl = getResignedObjectUrl(bucket, source.toString());
        } catch (Exception e) {
            throw new MinioException("Error while uploading file to Minio", e);
        }
        return fileUrl;
    }

    /**
     * 上传一个文件到 Minio
     *
     * @param source  带前缀的对象路径。必须包括对象名称。
     * @param file    作为输入流的文件
     * @param headers 要附加到文件上的额外头信息。此映射必须是可变的。所有自定义头信息在使用 {@code getMetadata()} 方法获取时会以 'x-amz-meta-' 前缀开始。
     * @throws MinioException 如果上传对象时发生错误
     */
    public void upload(Path source, InputStream file, Map<String, String> headers) throws MinioException {
        upload(source, file, headers, null);
    }

    /**
     * 上传一个文件到 Minio
     *
     * @param source  带前缀的对象路径。必须包括对象名称。
     * @param file    作为输入流的文件
     * @param headers 要附加到文件上的额外头信息。此映射必须是可变的。所有自定义头信息在使用 {@code getMetadata()} 方法获取时会以 'x-amz-meta-' 前缀开始。
     * @throws MinioException 如果上传对象时发生错误
     */
    public void upload(Path source, InputStream file, Map<String, String> headers, String bucket) throws MinioException {
        try {
            // 如果 bucket 参数为空，则使用默认配置中的 bucket 名称
            bucket = (bucket == null || bucket.isEmpty()) ? minioConfigurationProperties.getBucket() : bucket;
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(source.toString())
                    .stream(file, file.available(), -1)
                    .headers(headers)
                    .build();
            minioClient.putObject(args);
        } catch (Exception e) {
            throw new MinioException("Error while fetching files in Minio", e);
        }
    }

    /**
     * 上传一个文件到 Minio
     *
     * @param source 带前缀的对象路径。必须包括对象名称。
     * @param file   作为输入流的文件
     * @throws MinioException 如果上传对象时发生错误
     */
    public void upload(Path source, InputStream file) throws MinioException {
        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(minioConfigurationProperties.getBucket())
                    .object(source.toString())
                    .stream(file, file.available(), -1)
                    .build();
            minioClient.putObject(args);
        } catch (Exception e) {
            throw new MinioException("Error while fetching files in Minio", e);
        }
    }

    /**
     * 上传一个文件到 Minio
     *
     * @param source      带前缀的对象路径。必须包括对象名称。
     * @param file        作为输入流的文件
     * @param contentType 对象的 MIME 类型
     * @param headers     要附加到文件上的额外头信息。此映射必须是可变的
     * @throws MinioException 如果上传对象时发生错误
     */
    public void upload(Path source, InputStream file, String contentType, Map<String, String> headers) throws MinioException {
        upload(source, file, contentType, headers, null);
    }

    /**
     * 上传一个文件到 Minio
     *
     * @param source      带前缀的对象路径。必须包括对象名称。
     * @param file        作为输入流的文件
     * @param contentType 对象的 MIME 类型
     * @param headers     要附加到文件上的额外头信息。此映射必须是可变的
     * @param bucket      存储桶名称。若为空，则使用配置中的默认桶。
     * @throws MinioException 如果上传对象时发生错误
     */
    public void upload(Path source, InputStream file, String contentType, Map<String, String> headers, String bucket) throws MinioException {
        try {
            // 如果 bucket 参数为空，则使用默认配置中的 bucket 名称
            bucket = (bucket == null || bucket.isEmpty()) ? minioConfigurationProperties.getBucket() : bucket;

            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(source.toString())
                    .stream(file, file.available(), -1)
                    .headers(headers)
                    .contentType(contentType)
                    .build();
            minioClient.putObject(args);
        } catch (Exception e) {
            throw new MinioException("Error while uploading file to Minio", e);
        }
    }

    /**
     * 上传一个文件到 Minio
     * 处理大于 JVM 最大内存的文件
     *
     * @param source 带前缀的对象路径。必须包括对象名称。
     * @param file   作为文件名的文件
     * @throws MinioException 如果上传对象时发生错误
     */
    public void upload(Path source, File file) throws MinioException {
        upload(source, file, null);
    }

    /**
     * 上传一个文件到 Minio
     * 处理大于 JVM 最大内存的文件
     *
     * @param source 带前缀的对象路径。必须包括对象名称。
     * @param file   作为文件名的文件
     * @param bucket 指定存储桶的名称。如果为空，则使用默认存储桶
     * @throws MinioException 如果上传对象时发生错误
     */
    public void upload(Path source, File file, String bucket) throws MinioException {
        try {
            // 检查是否提供了存储桶名称，如果没有则使用默认存储桶
            String bucketName = (bucket != null && !bucket.isEmpty()) ? bucket : minioConfigurationProperties.getBucket();

            // 构建上传对象的参数
            UploadObjectArgs args = UploadObjectArgs.builder()
                    // 使用提供的存储桶名称或默认存储桶
                    .bucket(bucketName)
                    // 对象路径（包括名称）
                    .object(source.toString())
                    // 文件的绝对路径
                    .filename(file.getAbsolutePath())
                    .build();

            // 上传文件到 Minio
            minioClient.uploadObject(args);
        } catch (Exception e) {
            // 捕获并抛出自定义异常，包含错误信息
            throw new MinioException("Error while uploading file to Minio", e);
        }
    }

    /**
     * 获取文件的预签名 URL
     *
     * @param bucketName 存储桶名称
     * @param fileName   文件名称
     * @return 文件的 URL
     * @throws Exception 异常
     */
    public String getResignedObjectUrl(String bucketName, String fileName) throws Exception {
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .method(Method.GET)
                .build();
        return minioClient.getPresignedObjectUrl(args);
    }

    /**
     * 从 Minio 删除文件
     *
     * @param source 带前缀的对象路径。必须包括对象名称。
     * @throws MinioException 如果在删除对象时发生错误
     */
    public void delete(Path source) throws MinioException {
        delete(source, null);
    }

    /**
     * 从 Minio 删除一个对象
     *
     * @param source 带前缀的对象路径。必须包括对象名称。
     * @param bucket 指定存储桶的名称。如果为空，则使用默认存储桶
     * @throws MinioException 如果删除对象时发生错误
     */
    public void delete(Path source, String bucket) throws MinioException {
        try {
            // 如果未提供存储桶名称，则使用默认存储桶
            bucket = (bucket == null || bucket.isEmpty()) ? minioConfigurationProperties.getBucket() : bucket;

            // 构建删除对象的参数
            RemoveObjectArgs args = RemoveObjectArgs.builder()
                    // 使用指定的存储桶名称或默认存储桶
                    .bucket(bucket)
                    // 对象路径（包括名称）
                    .object(source.toString())
                    .build();
            // 从 Minio 删除对象
            minioClient.removeObject(args);
        } catch (Exception e) {
            throw new MinioException("Error while removing object from Minio", e);
        }
    }

    /**
     * 删除对象
     *
     * @param bucketName 存储桶名称
     * @param fileName   存储桶中的对象名称
     * @return 如果对象存在并已删除则返回 true，否则返回 false
     * @throws Exception 异常
     */
    public boolean delete(String bucketName, String fileName) throws Exception {
        if (existsBucket(bucketName)) {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build());
            return true;
        }
        return false;
    }

    /**
     * 删除指定存储桶中的多个文件对象，
     * 返回删除失败的对象列表。如果所有文件对象都成功删除，则返回空列表
     *
     * @param bucketName 存储桶名称
     * @param fileNames  包含多个要删除的对象名称的迭代器
     * @return 删除失败的对象列表
     * @throws Exception 异常
     */
    public List<String> delete(String bucketName, List<String> fileNames) throws Exception {
        if (CollectionUtils.isEmpty(fileNames)) {
            throw new MinioException("minio.delete.object.name.can.not.empty");
        }
        List<String> deleteErrorNames = new ArrayList<>();
        boolean flag = existsBucket(bucketName);
        if (flag) {
            List<DeleteObject> deleteFileNames = fileNames.stream().map(DeleteObject::new).toList();
            Iterable<Result<DeleteError>> results = minioClient
                    .removeObjects(RemoveObjectsArgs.builder().bucket(bucketName).objects(deleteFileNames).build());
            for (Result<DeleteError> result : results) {
                DeleteError error = result.get();
                deleteErrorNames.add(error.objectName());
            }
        }
        return deleteErrorNames;
    }

    /**
     * 上传文件并获取其短链接
     *
     * @param file        文件的输入流
     * @param source      文件路径，必须包含文件名
     * @param contentType 文件的 MIME 类型
     * @return 文件的短链接
     * @throws MinioException 如果上传文件或获取短链接时发生错误
     */
    public String uploadAndGetShortUrl(InputStream file, Path source, String contentType) throws MinioException {
        return uploadAndGetShortUrl(file, source, contentType, null);
    }

    /**
     * 上传文件并获取其短链接
     *
     * @param file        文件的输入流
     * @param source      文件路径，必须包含文件名
     * @param contentType 文件的 MIME 类型
     * @param bucket      存储桶名称。如果为 null 或空，则使用默认存储桶
     * @return 文件的短链接
     * @throws MinioException 如果上传文件或获取短链接时发生错误
     */
    public String uploadAndGetShortUrl(InputStream file, Path source, String contentType, String bucket) throws MinioException {
        String url;
        try {
            url = upload(file, source, contentType, bucket);
        } catch (MinioException e) {
            throw new MinioException("minio.delete.object.name.can.not.empty", e);
        }
        return url.substring(EventID.ACTION, url.indexOf("?"));
    }
}