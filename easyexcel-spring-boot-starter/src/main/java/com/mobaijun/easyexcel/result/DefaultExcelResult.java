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
package com.mobaijun.easyexcel.result;

import cn.hutool.core.util.StrUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

/**
 * Description: []
 * Author: [mobaijun]
 * Date: [2024/8/20 18:20]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Setter
public class DefaultExcelResult<T> implements ExcelResult<T> {

    /**
     * 数据对象list
     */
    private List<T> list;

    /**
     * 错误信息列表
     */
    private List<String> errorList;

    /**
     * 构造方法
     */
    public DefaultExcelResult() {
        this.list = new ArrayList<>();
        this.errorList = new ArrayList<>();
    }

    /**
     * 构造方法
     *
     * @param list      对象list
     * @param errorList 错误信息list
     */
    public DefaultExcelResult(List<T> list, List<String> errorList) {
        this.list = list;
        this.errorList = errorList;
    }

    /**
     * 构造方法
     *
     * @param excelResult 导入结果
     */
    public DefaultExcelResult(ExcelResult<T> excelResult) {
        this.list = excelResult.getList();
        this.errorList = excelResult.getErrorList();
    }

    @Override
    public List<T> getList() {
        return list;
    }

    @Override
    public List<String> getErrorList() {
        return errorList;
    }

    /**
     * 获取导入回执
     *
     * @return 导入回执
     */
    @Override
    public String getAnalysis() {
        int successCount = list.size();
        int errorCount = errorList.size();
        if (successCount == 0) {
            return "读取失败，未解析到数据";
        } else {
            if (errorCount == 0) {
                return StrUtil.format("恭喜您，全部读取成功！共{}条", successCount);
            } else {
                return "";
            }
        }
    }
}
