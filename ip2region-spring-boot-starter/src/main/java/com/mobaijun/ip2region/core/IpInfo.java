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
package com.mobaijun.ip2region.core;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

/**
 * Description: [IP信息]
 * Author: [mobaijun]
 * Date: [2024/8/15 10:21]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Getter
@Setter
@ToString
public class IpInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 国家
     */
    private String country;

    /**
     * 省
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区域
     */
    private String area;

    /**
     * 运营商
     */
    private String isp;

    /**
     * 原始IP
     */
    private String originIp;

    /**
     * No args constructor
     */
    public IpInfo() {
    }

    /**
     * <code>originIp</code> arg constructor
     *
     * @param originIp 原始IP
     */
    public IpInfo(String originIp) {
        this.originIp = originIp;
    }

    // region 拼接完整的IP区域信息的几个方法

    /**
     * 拼接完整的地址
     *
     * @return 描述IP区域信息的文本
     */
    public String getAddress() {
        return getAddress("");
    }

    /**
     * 拼接完整的地址
     *
     * @param delimiter 拼接用的分隔符
     * @return 描述IP区域信息的文本
     */
    public String getAddress(String delimiter) {
        Set<String> regionSet = new LinkedHashSet<>();
        regionSet.add(this.country);
        regionSet.add(this.province);
        regionSet.add(this.city);
        regionSet.add(this.area);
        regionSet.removeIf(Objects::isNull);
        return StringUtils.collectionToDelimitedString(regionSet, delimiter);
    }

    /**
     * 拼接完整的地址(带服务提供商)
     *
     * @return 描述IP区域信息的文本
     */
    public String getAddressAndIsp() {
        return getAddressAndIsp("");
    }

    /**
     * 拼接完整的地址(带服务提供商)
     *
     * @param delimiter 拼接用的分隔符
     * @return 描述IP区域信息的文本
     */
    public String getAddressAndIsp(String delimiter) {
        Set<String> regionSet = new LinkedHashSet<>();
        regionSet.add(this.country);
        regionSet.add(this.province);
        regionSet.add(this.city);
        regionSet.add(this.area);
        regionSet.add(this.isp);
        regionSet.removeIf(Objects::isNull);
        return StringUtils.collectionToDelimitedString(regionSet, delimiter);
    }
}
