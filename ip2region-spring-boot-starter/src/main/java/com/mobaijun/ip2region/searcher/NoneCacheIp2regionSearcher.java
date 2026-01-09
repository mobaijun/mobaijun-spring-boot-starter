/*
 * Copyright (C) 2022 [www.mobaijun.com]
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
package com.mobaijun.ip2region.searcher;

import com.mobaijun.ip2region.properties.Ip2regionProperties;
import org.lionsoul.ip2region.xdb.LongByteArray;
import org.lionsoul.ip2region.xdb.Searcher;
import org.lionsoul.ip2region.xdb.Version;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.InputStream;

/**
 * Description:
 * <p>
 * 完全基于文件的查询,不缓存,直接检索
 * <p>
 * 参考<a href=
 * "https://gitee.com/lionsoul/ip2region/tree/master/binding/java">ip2region</a>魔改
 * Author: [mobaijun]
 * Date: [2024/8/15 10:36]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class NoneCacheIp2regionSearcher extends Ip2regionSearcherTemplate {

    public NoneCacheIp2regionSearcher(ResourceLoader resourceLoader, Ip2regionProperties properties) {
        super(resourceLoader, properties);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Resource resource = this.resourceLoader.getResource(this.properties.getFileLocation());

        try (InputStream is = resource.getInputStream()) {
            LongByteArray longByteArray = new LongByteArray();
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                if (bytesRead < buffer.length) {
                    byte[] lastChunk = new byte[bytesRead];
                    System.arraycopy(buffer, 0, lastChunk, 0, bytesRead);
                    longByteArray.append(lastChunk);
                } else {
                    byte[] chunk = new byte[bytesRead];
                    System.arraycopy(buffer, 0, chunk, 0, bytesRead);
                    longByteArray.append(chunk);
                }
            }
            this.searcher = Searcher.newWithBuffer(Version.IPv4, longByteArray);
        }
    }
}
