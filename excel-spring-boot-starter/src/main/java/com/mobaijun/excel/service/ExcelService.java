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
package com.mobaijun.excel.service;

import com.mobaijun.excel.model.ExcelExportParam;
import java.io.InputStream;
import java.util.List;

/**
 * software：IntelliJ IDEA 2022.1
 * interface name: ExcelService
 * interface description：  Excel 常用操作接口
 *
 * @author MoBaiJun 2022/10/28 13:37
 */
public interface ExcelService {

    /**
     * 简单的导出Excel下载
     *
     * @param excelExportParam Excel导出参数
     */
    <T> void easyExportDownload(ExcelExportParam<T> excelExportParam);

    /**
     * 简单的写入Excel文件到指定路径
     *
     * @param excelExportParam Excel导出参数
     */
    <T> void easyWriteToFile(ExcelExportParam<T> excelExportParam);

    /**
     * 简单的读取Excel文件并返回实体类List集合
     *
     * @param inputStream 流输入Excel文件的流对象
     * @param clazz       每行数据转换成的对象类
     * @return 对象类List集合
     */
    <T> List<T> easyReadToList(InputStream inputStream, Class<T> clazz);

    /**
     * 简单的读取Excel文件并返回实体类List集合-针对多行表头
     *
     * @param inputStream 流输入Excel文件的流对象
     * @param clazz       每行数据转换成的对象类
     * @param rowNum      表头所占行数
     * @return 对象类List集合
     */
    <T> List<T> easyReadToList(InputStream inputStream, Integer rowNum, Class<T> clazz);
}
