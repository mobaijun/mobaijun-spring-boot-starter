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
import io.micrometer.common.lang.Nullable;

/**
 * Description: [IP 搜索接口]
 * Author: [mobaijun]
 * Date: [2024/8/15 10:38]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public interface Ip2regionSearcher {
    /**
     * ip 位置 搜索
     *
     * @param ip ip
     * @return 位置
     */
    @Nullable
    IpInfo search(@Nullable long ip);

    /**
     * ip 位置 搜索
     *
     * @param ip ip
     * @return 位置
     */
    @Nullable
    IpInfo search(@Nullable String ip);

    /**
     * 静默ip 位置 搜索
     *
     * @param ip ip
     * @return 位置
     */
    @Nullable
    IpInfo searchQuietly(@Nullable long ip);

    /**
     * 静默ip 位置 搜索
     *
     * @param ip ip
     * @return 位置
     */
    @Nullable
    IpInfo searchQuietly(@Nullable String ip);
}
