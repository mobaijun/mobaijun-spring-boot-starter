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
package com.mobaijun.bim.service.impl;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.bimface.exception.BimfaceException;
import com.bimface.sdk.BimfaceClient;
import com.mobaijun.bim.constant.BimApiConstant;
import com.mobaijun.bim.model.Data;
import com.mobaijun.bim.prop.BimProperties;
import com.mobaijun.bim.service.BimFaceClientService;
import com.mobaijun.bim.util.HttpKit;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: BimFaceClientServiceImpl
 * class description： bim 操作
 *
 * @author MoBaiJun 2022/10/10 10:19
 */
public class BimFaceClientServiceImpl implements BimFaceClientService {

    private final BimProperties bimProperties;

    public BimFaceClientServiceImpl(BimProperties bimProperties) {
        this.bimProperties = bimProperties;
    }

    @Override
    public BimfaceClient createBimFaceClient() {
        // appKey
        return new BimfaceClient(bimProperties.getAppKey(),
                // appSecret
                bimProperties.getAppSecret(),
                // API调用地址入口配置
                bimProperties.getEndpoint(),
                // bim 链接信息配置
                bimProperties.getConfig());
    }

    @Override
    public String getAccessToken() {
        try {
            return createBimFaceClient().getAccessTokenService().getAccessToken();
        } catch (BimfaceException e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    @Override
    public String getViewToken(String compareId, String fileId, String integrateId) {
        Map<String, Object> map = new ConcurrentHashMap<>(10);
        if (!compareId.isEmpty()) {
            map.put("compareId", compareId);
        }
        if (!fileId.isEmpty()) {
            map.put("fileId", fileId);
        }
        if (!integrateId.isEmpty()) {
            map.put("integrateId", integrateId);
        }
        map.put("Authorization", getAccessToken());
        return HttpUtil.post(BimApiConstant.VIEW_TOKEN_URL, map);
    }

    @Override
    public Data uploadBimFile(String name, MultipartFile multipartFile) {
        if (!StringUtils.hasText(name)) {
            throw new NullPointerException("文件的全名，使用URL编码（UTF-8），最多256个字符：name 为必填参数！");
        }
        Data data = getPolicyToken(name);
        Map<String, Object> dataS = new LinkedHashMap<>();
        dataS.put("name", "" + name + ".rvt");
        dataS.put("key", data.getObjectKey());
        dataS.put("policy", data.getPolicy());
        dataS.put("OSSAccessKeyId", data.getAccessId());
        dataS.put("callback", data.getCallbackBody());
        dataS.put("signature", data.getSignature());
        dataS.put("success_action_status", "200");
        Map<String, String> resultDataMap = HttpKit.sendMultipartFilePost(data.getHost(), multipartFile, "file", dataS, -1);
        // 返回消息体
        return JSONUtil.toBean(JSONUtil.parseObj(resultDataMap.get("data")).get("data").toString(), Data.class);
    }

    @Override
    public Data getPolicyToken(String name) {
        String json = HttpRequest.get(BimApiConstant.GET_POLICY + "?name=" + name)
                .header(Header.AUTHORIZATION.name(), "Bearer " + getAccessToken())
                .execute()
                .body();
        // 解析为对象
        return JSONUtil.toBean(JSONUtil.parseObj(json).get("data").toString(), Data.class);
    }

    @Override
    public Data getFileInform(String fileId) {
        String body = HttpRequest.get(BimApiConstant.FILE_INFO + fileId)
                .header(Header.AUTHORIZATION.name(), "Bearer " + getAccessToken())
                .execute()
                .body();
        return JSONUtil.toBean(JSONUtil.parseObj(body).get("data").toString(), Data.class);
    }

    @Override
    public Data translateBimFile(String fileId, String body) {
        if (!StringUtils.hasText(body)) {
            body = HttpRequest.put(BimApiConstant.BIM_TRANSLATE)
                    .header(Header.AUTHORIZATION.name(), "Bearer " + getAccessToken())
                    .body("{\n" +
                            "    \"source\":{\n" +
                            "        \"fileId\":" + fileId + ",\n" +
                            "        \"compressed\":false\n" +
                            "    },\n" +
                            "    \"callback\":\"https://www.app.com/receive\",\n" +
                            "    \"config\":{\n" +
                            "        \"toBimtiles\":true\n" +
                            "    }\n" +
                            "}")
                    .execute().body();
        }
        return JSONUtil.toBean(JSONUtil.parseObj(body).get("data").toString(), Data.class);
    }

    @Override
    public void deleteBimFile(String fileIds) {
        HttpRequest.delete(BimApiConstant.DELETE_BIM_FILE + "?fileId=" + fileIds)
                .header(Header.AUTHORIZATION.name(), "Bearer " + getAccessToken())
                .execute();
    }

    @Override
    public String getBimViewModel(String fileId, String gldProjectId) {
        return BimApiConstant.PREVIEW +
                "appKey=" +
                bimProperties.getAppKey() +
                "&projectId=" +
                gldProjectId +
                "&fileId=" +
                fileId;
    }
}