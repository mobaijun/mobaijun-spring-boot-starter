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
package com.mobaijun.file.config;

import com.mobaijun.file.core.FileClient;
import com.mobaijun.file.ftp.FtpFileClient;
import com.mobaijun.file.local.LocalFileClient;
import com.mobaijun.file.prop.FileProperties;
import com.mobaijun.file.prop.FileProperties.LocalProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: FileAutoConfiguration
 * class description： 文件自动配置类
 *
 * @author MoBaiJun 2022/9/26 14:46
 */
@AutoConfiguration
public class FileAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(FileClient.class)
    @ConditionalOnProperty(prefix = FileProperties.PREFIX_FTP, name = "ip")
    public FileClient fileFtpClient(FileProperties properties) {
        return new FtpFileClient(properties.getFtp());
    }

    @Bean
    @ConditionalOnMissingBean(FileClient.class)
    public FileClient fileLocalClient(FileProperties properties) throws IOException {
        LocalProperties localProperties = properties == null || properties.getLocal() == null ? new LocalProperties()
                : properties.getLocal();
        return new LocalFileClient(localProperties);
    }
}