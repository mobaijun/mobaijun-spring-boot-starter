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
package com.mobaijun.bim.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * HTTP请求
 *
 * @author mobai
 */
public class HttpKit {

    /**
     * 发送带文件的 HTTP POST 请求，支持上传 MultipartFile 文件。
     *
     * @param url           请求的URL地址
     * @param multipartFile 要上传的 MultipartFile 文件
     * @param fileParName   文件参数的名称
     * @param params        其他文本参数的键值对
     * @param timeout       请求超时时间（毫秒）
     * @return 包含响应状态码和数据的 Map
     */
    public static Map<String, String> sendMultipartFilePost(String url, MultipartFile multipartFile, String fileParName,
                                                            Map<String, Object> params, int timeout) {
        Map<String, String> resultMap = new HashMap<>(10);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result;
        try {
            HttpPost httpPost = new HttpPost(url);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            String fileName = multipartFile.getOriginalFilename();

            ContentType contentType = ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), StandardCharsets.UTF_8);

            // 添加文本参数
            params.entrySet().stream()
                    .filter(entry -> Objects.nonNull(entry.getValue()))
                    .forEach(entry -> builder.addTextBody(entry.getKey(), entry.getValue().toString(), contentType));

            // 添加文件流
            builder.addBinaryBody(fileParName, multipartFile.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);

            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);

            // 设置连接超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(timeout)
                    .setConnectionRequestTimeout(timeout)
                    .setSocketTimeout(timeout)
                    .build();
            httpPost.setConfig(requestConfig);

            // 执行提交
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity responseEntity = response.getEntity();
                resultMap.put("scode", String.valueOf(response.getStatusLine().getStatusCode()));
                resultMap.put("data", "");

                if (Objects.nonNull(responseEntity)) {
                    // 将响应内容转换为字符串
                    result = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
                    resultMap.put("data", result);
                }
            }
        } catch (IOException e) {
            resultMap.put("scode", "error");
            resultMap.put("data", "HTTP请求出现异常: " + e.getMessage());
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }
}