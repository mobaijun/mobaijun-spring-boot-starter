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
package com.mobaijun.easyexcel.listener;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.mobaijun.common.collection.StreamUtil;
import com.mobaijun.core.util.ValidatorUtil;
import com.mobaijun.easyexcel.result.DefaultExcelResult;
import com.mobaijun.easyexcel.result.ExcelResult;
import com.mobaijun.json.utils.JsonUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Map;
import java.util.Set;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Description: Excel 导入监听器
 * Author: [mobaijun]
 * Date: [2024/8/20 18:20]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
@NoArgsConstructor
public class DefaultExcelListener<T> extends AnalysisEventListener<T> implements ExcelListener<T> {

    /**
     * 是否Validator检验，默认为是
     */
    private Boolean isValidate = Boolean.TRUE;

    /**
     * excel 表头数据
     */
    private Map<Integer, String> headMap;

    /**
     * 导入回执
     */
    private ExcelResult<T> excelResult;

    public DefaultExcelListener(boolean isValidate) {
        this.excelResult = new DefaultExcelResult<>();
        this.isValidate = isValidate;
    }

    /**
     * 处理异常
     *
     * @param exception ExcelDataConvertException
     * @param context   Excel 上下文
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        String errMsg = null;
        if (exception instanceof ExcelDataConvertException excelDataConvertException) {
            // 如果是某一个单元格的转换异常 能获取到具体行号
            Integer rowIndex = excelDataConvertException.getRowIndex();
            Integer columnIndex = excelDataConvertException.getColumnIndex();
            errMsg = StrUtil.format("第{}行-第{}列-表头{}: 解析异常<br/>",
                    rowIndex + 1, columnIndex + 1, headMap.get(columnIndex));
            if (log.isDebugEnabled()) {
                log.error(errMsg);
            }
        }
        if (exception instanceof ConstraintViolationException constraintViolationException) {
            Set<ConstraintViolation<?>> constraintViolations = constraintViolationException.getConstraintViolations();
            String constraintViolationsMsg = StreamUtil.join(constraintViolations, ConstraintViolation::getMessage, ", ");
            errMsg = StrUtil.format("第{}行数据校验异常: {}", context.readRowHolder().getRowIndex() + 1, constraintViolationsMsg);
            if (log.isDebugEnabled()) {
                log.error(errMsg);
            }
        }
        excelResult.getErrorList().add(errMsg);
        throw new ExcelAnalysisException(errMsg);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        this.headMap = headMap;
        log.debug("解析到一条表头数据: {}", JsonUtil.toJsonString(headMap));
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        if (isValidate) {
            ValidatorUtil.validate(data);
        }
        excelResult.getList().add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.debug("所有数据解析完成！");
    }

    @Override
    public ExcelResult<T> getExcelResult() {
        return excelResult;
    }
}
