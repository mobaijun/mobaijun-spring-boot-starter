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
package com.mobaijun.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import java.util.ArrayList;
import java.util.List;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: SimpleDataListener
 * class description： 数据监听器
 *
 * @author MoBaiJun 2022/10/28 13:39
 */
public class ExcelDataListener<T> extends AnalysisEventListener<T> {

    /**
     * 实体类List集合
     */
    private final List<T> dataList = new ArrayList<>();

    /**
     * 获取实体类List集合
     *
     * @return 实体类List集合
     */
    public List<T> getDataList() {
        return dataList;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    Excel每行数据转换成的对象类
     * @param context EasyExcel分析上下文
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        // 添加到集合中
        dataList.add(data);
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context EasyExcel分析上下文
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
    }
}
