/*
 * Copyright (C) 2022 [%s]
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
package com.mobaijun;

import com.mobaijun.common.enums.comm.LicenseType;
import com.mobaijun.common.license.LicenseTitleAppenderUtil;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        String format = "D:\\ideaProject\\my-project\\mobaijun-spring-boot-dependencies\\core-spring-boot-starter\\src\\main\\java\\com\\mobaijun\\core";
        LicenseTitleAppenderUtil.append(format, String.format(LicenseType.APACHE_2.getValue(), "www.mobaijun.com"));
        // LicenseTitleAppenderUtil.removeLicense(format, String.format(LicenseType.APACHE_2.getValue(), "www.mobaijun.com"));
    }
}
