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
import org.lionsoul.ip2region.xdb.Searcher;
import org.lionsoul.ip2region.xdb.Version;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Description:
 * 缓存索引数据实现检索
 * <p>
 * 提前从 xdb 文件中加载出来 VectorIndex 数据，然后全局缓存，每次创建 Searcher 对象的时候使用全局的 VectorIndex 缓存可以减少一次固定的
 * IO 操作，从而加速查询，减少 IO 压力
 * </p>
 * <p>
 * 参考<a href=
 * "https://gitee.com/lionsoul/ip2region/tree/master/binding/java">ip2region</a>魔改
 * </p>
 * Author: [mobaijun]
 * Date: [2024/8/15 10:22]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class CacheVectorIndexIp2regionSearcher extends Ip2regionSearcherTemplate {

    public CacheVectorIndexIp2regionSearcher(ResourceLoader resourceLoader, Ip2regionProperties properties) {
        super(resourceLoader, properties);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Resource resource = this.resourceLoader.getResource(this.properties.getFileLocation());
        File xdbFile;
        try {
            xdbFile = resource.getFile();
        } catch (IOException e) {
            xdbFile = File.createTempFile("ip2region_", ".xdb");
            try (InputStream is = resource.getInputStream();
                 OutputStream os = new FileOutputStream(xdbFile)) {
                StreamUtils.copy(is, os);
            }
            xdbFile.deleteOnExit();
        }
        String dbPath = xdbFile.getPath();
        byte[] vIndex = Searcher.loadVectorIndexFromFile(dbPath);
        this.searcher = Searcher.newWithVectorIndex(Version.IPv4, xdbFile, vIndex);
    }
}
