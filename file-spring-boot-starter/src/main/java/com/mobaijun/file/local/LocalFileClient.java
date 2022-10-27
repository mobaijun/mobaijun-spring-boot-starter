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
package com.mobaijun.file.local;

import cn.hutool.core.io.FileUtil;
import com.mobaijun.file.core.AbstractFileClient;
import com.mobaijun.file.exception.FileException;
import com.mobaijun.file.prop.FileProperties.LocalProperties;
import com.mobaijun.file.util.StreamUtil;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: LocalFileClient
 * class description： 本地文件客户端
 *
 * @author MoBaiJun 2022/9/26 14:48
 */
public class LocalFileClient extends AbstractFileClient {

    /**
     * 父目录
     */
    private final File parentDir;

    public LocalFileClient(LocalProperties properties) throws IOException {
        final File dir = StringUtils.hasText(properties.getPath()) ? new File(properties.getPath())
                : FileUtil.getTmpDir();
        // 不存在且创建失败
        if (!dir.exists() && !dir.mkdirs()) {
            throw new FileException(String.format("Path: %s; does not exist and failed to create! Please check if you have permission to operate on this path!", dir.getPath()));
        }
        parentDir = dir;
        super.rootPath = dir.getPath();
        super.slash = File.separator;
    }

    /**
     * 文件上传
     *
     * @param relativePath 文件相对 getRoot() 的路径
     * @param stream       文件输入流
     * @return 文件绝对路径
     */
    @Override
    public String uploadFile(InputStream stream, String relativePath) throws IOException {
        // 目标文件
        final File file = new File(parentDir, relativePath);

        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            throw new FileException("File upload failed! Failed to create parent folder! Parent path: " + file.getParentFile().getPath());
        }

        if (!file.exists() && !file.createNewFile()) {
            throw new FileException("File upload failed! File creation failed! File path: " + file.getPath());
        }

        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            StreamUtil.write(stream, outputStream);
        }

        return relativePath;
    }

    /**
     * 下载文件
     *
     * @param relativePath 文件相对 getRoot() 的路径
     * @return java.io.FileOutputStream 文件流
     */
    @Override
    public File downloadFile(String relativePath) {
        return new File(parentDir, relativePath);
    }

    /**
     * 删除文件
     *
     * @param relativePath 文件相对 getRoot() 的路径
     * @return boolean
     * @author lingting 2021-10-18 17:14
     */
    @Override
    public boolean deleteFile(String relativePath) {
        final File file = new File(parentDir, relativePath);
        return file.exists() && file.delete();
    }
}