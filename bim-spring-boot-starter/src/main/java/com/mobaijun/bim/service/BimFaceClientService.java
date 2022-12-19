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
package com.mobaijun.bim.service;

import com.bimface.sdk.BimfaceClient;
import com.mobaijun.bim.model.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * software：IntelliJ IDEA 2022.1
 * interface name: BimFaceClientService
 * interface description： bim 操作接口
 *
 * @author MoBaiJun 2022/10/10 10:17
 */
public interface BimFaceClientService {

    /**
     * 创建bim 客户端
     *
     * @return bim 客户端
     */
    BimfaceClient createBimFaceClient();

    /**
     * 获取 Access Token
     *
     * @return access token
     */
    String getAccessToken();

    /**
     * 获取模型的View Token
     *
     * @param compareId   模型对比ID(与文件转换ID、集成模型ID，三选一)
     * @param fileId      文件转换ID(与集成模型ID、模型对比ID，三选一)
     * @param integrateId 集成模型ID(与文件转换ID、模型对比ID，三选一)
     * @return 模型的View Token
     */
    String getViewToken(String compareId, String fileId, String integrateId);


    /**
     * 上传 bim 文件（根据policy凭证在web端上传文件）
     *
     * @param name          文件的全名，使用URL编码（UTF-8），最多256个字符
     * @param multipartFile bim 文件
     * @return 是否成功
     */
    Data uploadBimFile(String name, MultipartFile multipartFile);

    /**
     * 获取文件直传的policy凭证
     *
     * @param name 文件的全名，使用URL编码（UTF-8），最多256个字符
     * @return policy凭证
     */
    Data getPolicyToken(String name);

    /**
     * 根据文件ID获取文件详细信息
     *
     * @param fileId 文件ID
     * @return data
     */
    Data getFileInform(String fileId);

    /**
     * 发起源文件/模型转换
     *
     * @param fileId bim 文件 id
     * @param body   FileTranslateRequest请求体
     * @return Data
     */
    Data translateBimFile(String fileId, String body);

    /**
     * 删除 bim 模型
     *
     * @param fileIds id 列表
     */
    void deleteBimFile(String fileIds);

    /**
     * 查看 bim 模型
     *
     * @param fileId       bim id
     * @param gldProjectId 广联达项目id
     * @return url 地址
     */
    String getBimViewModel(String fileId, String gldProjectId);
}