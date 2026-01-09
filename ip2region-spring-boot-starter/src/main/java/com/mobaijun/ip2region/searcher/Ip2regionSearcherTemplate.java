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

import com.mobaijun.ip2region.core.IpInfo;
import com.mobaijun.ip2region.properties.Ip2regionProperties;
import com.mobaijun.ip2region.util.IpInfoUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.lionsoul.ip2region.xdb.Searcher;
import org.lionsoul.ip2region.xdb.Util;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ResourceLoader;

/**
 * Description: [Ip2region 搜索服务实现]
 * Author: [mobaijun]
 * Date: [2024/8/15 10:35]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@RequiredArgsConstructor
public abstract class Ip2regionSearcherTemplate implements DisposableBean, InitializingBean, Ip2regionSearcher {

    protected final ResourceLoader resourceLoader;
    protected final Ip2regionProperties properties;
    protected Searcher searcher;

    @Override
    @SneakyThrows({Exception.class})
    public IpInfo search(long ip) {
        byte[] ipBytes = new byte[]{
                (byte) ((ip >> 24) & 0xFF),
                (byte) ((ip >> 16) & 0xFF),
                (byte) ((ip >> 8) & 0xFF),
                (byte) (ip & 0xFF)
        };
        String region = this.searcher.search(ipBytes);
        String ipStr = String.format("%d.%d.%d.%d",
                (ipBytes[0] & 0xFF), (ipBytes[1] & 0xFF),
                (ipBytes[2] & 0xFF), (ipBytes[3] & 0xFF));
        return IpInfoUtil.toIpInfo(ipStr, region);
    }

    @Override
    @SneakyThrows({Exception.class})
    public IpInfo search(String ip) {
        if (ip == null || ip.isEmpty()) return null;

        // 使用 Util 类的解析方法将字符串转为 byte[]
        byte[] ipBytes = Util.parseIP(ip);
        String region = this.searcher.search(ipBytes);

        return IpInfoUtil.toIpInfo(ip, region);
    }

    @Override
    public IpInfo searchQuietly(long ip) {
        try {
            return search(ip);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public IpInfo searchQuietly(String ip) {
        try {
            return search(ip);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void destroy() throws Exception {
        if (this.searcher != null) {
            this.searcher.close();
        }
    }
}