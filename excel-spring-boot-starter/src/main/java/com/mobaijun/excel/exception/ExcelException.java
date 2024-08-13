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
package com.mobaijun.excel.exception;

import com.mobaijun.excel.constant.ExcelConstants;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: ExcelException
 * class description： Excel 操作异常信息
 *
 * @author MoBaiJun 2022/10/27 16:00
 */
public class ExcelException extends RuntimeException {

    public ExcelException(Throwable message) {
        super(ExcelConstants.OFFICE_MODULE_NAME, message);
    }

    public ExcelException(String code, Throwable message) {
        super(code,message);
    }
}
