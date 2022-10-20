package com.mobaijun.bim.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * HTTP请求
 *
 * @author mobai
 */
public class HttpKit {

    /**
     * logger
     */
    private static final Logger log = LoggerFactory.getLogger(HttpKit.class);
    private static final CloseableHttpClient HTTPCLIENT = HttpClients.createDefault();
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36";

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
            String fileName;
            fileName = multipartFile.getOriginalFilename();
            //解决中文乱码
            ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, StandardCharsets.UTF_8);
            for (Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() == null) {
                    continue;
                }
                // 类似浏览器表单提交，对应input的name和value
                builder.addTextBody(entry.getKey(), entry.getValue().toString(), contentType);
            }
            // 文件流
            builder.addBinaryBody(fileParName, multipartFile.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            // 执行提交
            HttpResponse response = httpClient.execute(httpPost);

            // 设置连接超时时间
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout)
                    .setConnectionRequestTimeout(timeout).setSocketTimeout(timeout).build();
            httpPost.setConfig(requestConfig);

            HttpEntity responseEntity = response.getEntity();
            resultMap.put("scode", String.valueOf(response.getStatusLine().getStatusCode()));
            resultMap.put("data", "");
            if (responseEntity != null) {
                // 将响应内容转换为字符串
                result = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
                resultMap.put("data", result);
            }
        } catch (Exception e) {
            resultMap.put("scode", "error");
            resultMap.put("data", "HTTP请求出现异常: " + e.getMessage());

            Writer w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
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