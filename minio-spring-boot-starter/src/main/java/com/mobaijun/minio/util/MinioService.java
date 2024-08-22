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
 * class description： Service class to interact with Minio bucket.
 * This class is register as a bean and use the properties defined
 * in {@link MinioConfigurationProperties}.
 * All methods return an {@link MinioException} which wrap the Minio SDK exception.
 * The bucket name is provided with the one defined in the configuration properties.
 *
 * @author MoBaiJun 2022/9/19 17:37
 */
public class MinioService {

    private final MinioClient minioClient;

    private final MinioConfigurationProperties minioConfigurationProperties;


    public MinioService(MinioClient minioClient, MinioConfigurationProperties minioProperties) {
        this.minioClient = minioClient;
        this.minioConfigurationProperties = minioProperties;
    }

    /**
     * Check whether the bucket exists
     *
     * @param bucketName Bucket Name
     * @return If there is a
     */
    public boolean bucketExists(String bucketName) throws Exception {
        return minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(bucketName)
                .build()
        );
    }

    /**
     * Creating a Bucket
     *
     * @param bucketName Bucket Name
     */
    public void makeBucket(String bucketName) throws Exception {
        if (!bucketExists(bucketName)) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    /**
     * Lists all buckets
     *
     * @return Bucket List
     */
    public List<Bucket> listBuckets() throws Exception {
        return minioClient.listBuckets();
    }

    /**
     * Lists all bucket names
     *
     * @return Bucket List Name
     */
    public List<String> listBucketNames() throws Exception {
        List<Bucket> bucketList = listBuckets();
        return !CollectionUtils.isEmpty(bucketList) ?
                bucketList.stream().map(Bucket::name)
                        .toList() : new ArrayList<>();
    }

    /**
     * Deleting a Bucket
     *
     * @param bucketName Bucket Name
     * @return The success of
     */
    public boolean removeBucket(String bucketName) throws Exception {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            Iterable<Result<Item>> myObjects = listObjects(bucketName);
            for (Result<Item> result : myObjects) {
                Item item = result.get();
                // If an object file exists, the file fails to be deleted
                if (item.size() > 0) {
                    return false;
                }
            }
            // The bucket can be deleted only when the bucket is empty.
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
            flag = bucketExists(bucketName);
            return !flag;
        }
        return false;
    }

    /**
     * Lists all the object names in the bucket
     *
     * @param bucketName Bucket Name
     * @return The list of
     */
    public List<String> listObjectNames(String bucketName) throws Exception {
        List<String> listObjectNames = new ArrayList<>();
        if (bucketExists(bucketName)) {
            Iterable<Result<Item>> myObjects = listObjects(bucketName);
            for (Result<Item> result : myObjects) {
                Item item = result.get();
                listObjectNames.add(item.objectName());
            }
        }
        return listObjectNames;
    }

    /**
     * Lists all objects in the bucket
     *
     * @param bucketName Bucket Name
     * @return Barrel object
     */
    public Iterable<Result<Item>> listObjects(String bucketName) throws Exception {
        if (bucketExists(bucketName)) {
            return minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
        }
        return null;
    }

    /**
     * Obtaining a list of files
     *
     * @return File list
     */
    public List<String> listFiles() {
        // Gets a list of file objects in the bucket
        return !CollectionUtils.isEmpty(list()) ? list().stream()
                .map(Item::objectName)
                .toList() : new LinkedList<>();
    }

    /**
     * List all objects at root of the bucket
     *
     * @return List of items
     */
    public List<Item> list() {
        ListObjectsArgs args = ListObjectsArgs.builder()
                .bucket(minioConfigurationProperties.getBucket())
                .prefix("")
                .recursive(false)
                .build();
        return getItems(minioClient.listObjects(args));
    }


    /**
     * Get file temporary url
     *
     * @param path The relative path of the file in the bucket
     * @return The address of the file in the bucket
     */
    public String getUrl(String path) throws MinioException {
        GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                .bucket(minioConfigurationProperties.getBucket())
                .object(path)
                .method(Method.GET)
                .expiry(Math.toIntExact(minioConfigurationProperties.getExpire().getSeconds()), TimeUnit.MINUTES)
                .build();
        try {
            return minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs);
        } catch (Exception e) {
            throw new MinioException("Error getting file list", e);
        }
    }

    /**
     * List all objects at root of the bucket
     *
     * @return List of items
     */
    public List<Item> fullList() {
        ListObjectsArgs args = ListObjectsArgs.builder()
                .bucket(minioConfigurationProperties.getBucket())
                .build();
        return getItems(minioClient.listObjects(args));
    }

    /**
     * List all objects with the prefix given in parameter for the bucket.
     * Simulate a folder hierarchy. Objects within folders (i.e. all objects which match the pattern
     * {@code {prefix}/{objectName}/...}) are not returned
     *
     * @param path Prefix of seeked list of object
     * @return List of items
     */
    public List<Item> list(Path path) {
        ListObjectsArgs args = ListObjectsArgs.builder()
                .bucket(minioConfigurationProperties.getBucket())
                .prefix(path.toString())
                .recursive(false)
                .build();
        Iterable<Result<Item>> myObjects = minioClient.listObjects(args);
        return getItems(myObjects);
    }

    /**
     * List all objects with the prefix given in parameter for the bucket
     * <p>
     * All objects, even those which are in a folder are returned.
     *
     * @param path Prefix of seeked list of object
     * @return List of items
     */
    public List<Item> getFullList(Path path) {
        ListObjectsArgs args = ListObjectsArgs.builder()
                .bucket(minioConfigurationProperties.getBucket())
                .prefix(path.toString())
                .build();
        Iterable<Result<Item>> myObjects = minioClient.listObjects(args);
        return getItems(myObjects);
    }

    /**
     * Utility method which map results to items and return a list
     *
     * @param myObjects Iterable of results
     * @return List of items
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
     * Get an object from Minio
     *
     * @param path Path with prefix to the object. Object name must be included.
     * @return The object as an InputStream
     * @throws MinioException if an error occur while fetch object
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
     * Get metadata of an object from Minio
     *
     * @param path Path with prefix to the object. Object name must be included.
     * @return Metadata of the  object
     * @throws MinioException if an error occur while fetching object metadatas
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
     * Get metadata for multiples objects from Minio
     *
     * @param paths Paths of all objects with prefix. Object names must be included.
     * @return A map where all paths are keys and metadata are values
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
     * Get a file from Minio, and save it in the {@code fileName} file
     *
     * @param source   Path with prefix to the object. Object name must be included.
     * @param fileName Filename
     * @throws MinioException if an error occur while fetch object
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
     * Upload a file to Minio
     *
     * @param source  Path with prefix to the object. Object name must be included.
     * @param file    File as an input stream
     * @param headers Additional headers to put on the file. The map MUST be mutable. All custom headers will start with 'x-amz-meta-'
     *                prefix when fetched with {@code getMetadata()} method.
     * @throws MinioException if an error occur while uploading object
     */
    public void upload(Path source, InputStream file, Map<String, String> headers) throws
            MinioException {
        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(minioConfigurationProperties.getBucket())
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
     * Upload a file to Minio
     *
     * @param source Path with prefix to the object. Object name must be included.
     * @param file   File as an input stream
     * @throws MinioException if an error occur while uploading object
     */
    public void upload(Path source, InputStream file) throws
            MinioException {
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
     * Upload a file to Minio
     *
     * @param source      Path with prefix to the object. Object name must be included.
     * @param file        File as an input stream
     * @param contentType MIME type for the object
     * @param headers     Additional headers to put on the file. The map MUST be mutable
     * @throws MinioException if an error occur while uploading object
     */
    public void upload(Path source, InputStream file, String contentType, Map<String, String> headers) throws
            MinioException {
        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(minioConfigurationProperties.getBucket())
                    .object(source.toString())
                    .stream(file, file.available(), -1)
                    .headers(headers)
                    .contentType(contentType)
                    .build();
            minioClient.putObject(args);
        } catch (Exception e) {
            throw new MinioException("Error while fetching files in Minio", e);
        }
    }

    /**
     * Upload a file to Minio
     *
     * @param source      Path with prefix to the object. Object name must be included.
     * @param file        File as an input stream
     * @param contentType MIME type for the object
     * @throws MinioException if an error occur while uploading object
     */
    public void upload(Path source, InputStream file, String contentType) throws MinioException {
        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(minioConfigurationProperties.getBucket())
                    .object(source.toString())
                    .stream(file, file.available(), -1)
                    .contentType(contentType)
                    .build();
            minioClient.putObject(args);
        } catch (Exception e) {
            throw new MinioException("Error while fetching files in Minio", e);
        }
    }

    /**
     * Upload a file to Minio
     * upload file bigger than Xmx size
     *
     * @param source Path with prefix to the object. Object name must be included.
     * @param file   File as a Filename
     * @throws MinioException if an error occur while uploading object
     */
    public void upload(Path source, File file) throws MinioException {
        try {
            UploadObjectArgs args = UploadObjectArgs.builder()
                    .bucket(minioConfigurationProperties.getBucket())
                    .object(source.toString())
                    .filename(file.getName())
                    .build();
            minioClient.uploadObject(args);
        } catch (Exception e) {
            throw new MinioException("Error while fetching files in Minio", e);
        }
    }

    /**
     * Get file outreach
     *
     * @param bucketName Bucket Name
     * @param fileName   The file name
     * @return The file url
     * @throws Exception Exception
     */
    public String getResignedObjectUrl(String bucketName, String fileName) throws Exception {
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .method(Method.GET).build();
        return minioClient.getPresignedObjectUrl(args);
    }

    /**
     * Remove a file to Minio
     *
     * @param source Path with prefix to the object. Object name must be included.
     */
    public void remove(Path source) throws MinioException {
        try {
            RemoveObjectArgs args = RemoveObjectArgs.builder()
                    .bucket(minioConfigurationProperties.getBucket())
                    .object(source.toString())
                    .build();
            minioClient.removeObject(args);
        } catch (Exception e) {
            throw new MinioException("Error while fetching files in Minio", e);
        }
    }

    /**
     * Upload a file to Minio And return the link
     * upload file bigger than Xmx size
     *
     * @param source      Path with prefix to the object. Object name must be included.
     * @param file        File as a Filename
     * @param contentType Request file type
     * @return Image links
     * @throws MinioException if an error occur while uploading object
     */
    public String upload(InputStream file, Path source, String contentType) throws MinioException {
        String fileUrl;
        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(minioConfigurationProperties.getBucket())
                    .object(source.toString())
                    .stream(file, file.available(), -1)
                    .contentType(contentType)
                    .build();
            minioClient.putObject(args);
            // result
            fileUrl = getResignedObjectUrl(minioConfigurationProperties.getBucket(), source.toString());
        } catch (Exception e) {
            throw new MinioException("Error while fetching files in Minio", e);
        }
        return fileUrl;
    }

    /**
     * Deleting an object
     *
     * @param bucketName Bucket Name
     * @param fileName   The name of the object in the bucket
     */
    public boolean removeObject(String bucketName, String fileName) throws Exception {
        if (bucketExists(bucketName)) {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName).build());
            return true;
        }
        return false;
    }

    /**
     * If multiple file objects in the specified bucket are deleted,
     * a list of incorrect objects is displayed.
     * If all file objects are successfully deleted, an empty list is displayed
     *
     * @param bucketName Bucket Name
     * @param fileNames  Iterator object containing multiple object names to delete
     * @return Delete the list
     */
    public List<String> removeObject(String bucketName, List<String> fileNames) throws Exception {
        if (CollectionUtils.isEmpty(fileNames)) {
            throw new MinioException("minio.delete.object.name.can.not.empty");
        }
        List<String> deleteErrorNames = new ArrayList<>();
        boolean flag = bucketExists(bucketName);
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
     * Get a short link to the file
     *
     * @param file        file
     * @param source      source
     * @param contentType contentType
     * @return url
     */
    public String interceptUrl(InputStream file, Path source, String contentType) throws MinioException {
        String url;
        try {
            url = upload(file, source, contentType);
        } catch (MinioException e) {
            throw new MinioException("minio.delete.object.name.can.not.empty", e);
        }
        return url.substring(EventID.ACTION, url.indexOf("?"));
    }
}