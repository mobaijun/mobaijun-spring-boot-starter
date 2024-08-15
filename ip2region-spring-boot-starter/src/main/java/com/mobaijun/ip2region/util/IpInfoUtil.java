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
package com.mobaijun.ip2region.util;

import com.mobaijun.ip2region.core.IpInfo;
import io.micrometer.common.lang.Nullable;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Description: [IP 工具类]
 * Author: [mobaijun]
 * Date: [2024/8/15 10:37]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class IpInfoUtil {

    /**
     * 缓存正则表达式，提升编译速度
     */
    private static final Pattern SPLIT_PATTERN = Pattern.compile("\\|");
    /**
     * ip2Region 采用 0 填充的没有数据的字段
     */
    private static final String NO_DATA = "0";

    private IpInfoUtil() {
    }

    /**
     * 将 DataBlock 转化为 IpInfo
     *
     * @param dataBlock 数据块,格式为<code>国家|区域|省份|城市|ISP</code>
     * @return IpInfo
     */
    @Nullable
    public static IpInfo toIpInfo(@Nullable String dataBlock) {
        return toIpInfo(null, dataBlock);
    }

    /**
     * 将 DataBlock 转化为 IpInfo
     *
     * @param originIp  原始IP信息
     * @param dataBlock 数据块,格式为<code>国家|区域|省份|城市|ISP</code>
     * @return IpInfo
     */
    @Nullable
    public static IpInfo toIpInfo(@Nullable String originIp, @Nullable String dataBlock) {
        if (dataBlock == null) {
            return null;
        }
        IpInfo ipInfo = new IpInfo(originIp);
        String[] tmp = SPLIT_PATTERN.split(dataBlock);
        // 补齐5位
        if (tmp.length < 5) {
            tmp = Arrays.copyOf(tmp, 5);
        }
        ipInfo.setCountry(filterZero(tmp[0]));
        ipInfo.setArea(filterZero(tmp[1]));
        ipInfo.setProvince(filterZero(tmp[2]));
        ipInfo.setCity(filterZero(tmp[3]));
        ipInfo.setIsp(filterZero(tmp[4]));
        return ipInfo;
    }

    /**
     * 数据过滤，因为 ip2Region 采用 0 填充的没有数据的字段
     *
     * @param info info
     * @return info
     */
    @Nullable
    private static String filterZero(@Nullable String info) {
        // null 或 0 返回 null
        if (info == null || NO_DATA.equals(info)) {
            return null;
        }
        return info;
    }
}
