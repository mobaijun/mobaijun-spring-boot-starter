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
package com.mobaijun.easyexcel.handler;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.metadata.data.DataFormatData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.mobaijun.core.util.reflect.ReflectUtil;
import com.mobaijun.easyexcel.annotation.ExcelNotation;
import com.mobaijun.easyexcel.annotation.ExcelRequired;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

/**
 * Description:
 * <p>
 * DataWriteHandler 负责处理数据写入到 Excel 表格。
 * 它实现了 SheetWriteHandler 和 CellWriteHandler 接口。
 * 该处理器根据自定义注解管理单元格样式和批注。
 * <p>
 * Author: [mobaijun]
 * Date: [2024/12/27 9:32]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@RequiredArgsConstructor
public class DataWriteHandler implements SheetWriteHandler, CellWriteHandler {

    /**
     * 批注映射。
     * 键：列索引
     * 值：批注文本
     */
    private final Map<Integer, String> notationMap;

    /**
     * 头列字体颜色映射。
     * 键：列索引
     * 值：字体颜色索引
     */
    private final Map<Integer, Short> headColumnMap;

    /**
     * 构造函数，根据提供的类初始化 notationMap 和 headColumnMap。
     *
     * @param clazz 要从中提取注解的类。
     */
    public DataWriteHandler(Class<?> clazz) {
        notationMap = getNotationMap(clazz);
        headColumnMap = getRequiredMap(clazz);
    }

    /**
     * 从提供的类中获取必填列及其字体颜色的映射。
     *
     * @param clazz 要从中提取必填列注解的类。
     * @return 一个映射，其中键是列索引，值是字体颜色索引。
     */
    private static Map<Integer, Short> getRequiredMap(Class<?> clazz) {
        Map<Integer, Short> requiredMap = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        // 检查 fields 数组是否为空
        if (fields.length == 0) {
            return requiredMap;
        }
        Field[] filteredFields = ReflectUtil.getFields(clazz, field -> !"serialVersionUID".equals(field.getName()));

        for (int i = 0; i < filteredFields.length; i++) {
            Field field = filteredFields[i];
            if (!field.isAnnotationPresent(ExcelRequired.class)) {
                continue;
            }
            ExcelRequired excelRequired = field.getAnnotation(ExcelRequired.class);
            int columnIndex = excelRequired.index() == -1 ? i : excelRequired.index();
            requiredMap.put(columnIndex, excelRequired.fontColor().getIndex());
        }
        return requiredMap;
    }

    /**
     * 从提供的类中获取单元格批注的映射。
     *
     * @param clazz 要从中提取批注的类。
     * @return 一个映射，其中键是列索引，值是批注文本。
     */
    private static Map<Integer, String> getNotationMap(Class<?> clazz) {
        Map<Integer, String> notationMap = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        // 检查 fields 数组是否为空
        if (fields.length == 0) {
            return notationMap;
        }
        Field[] filteredFields = ReflectUtil.getFields(clazz, field -> !"serialVersionUID".equals(field.getName()));
        for (int i = 0; i < filteredFields.length; i++) {
            Field field = filteredFields[i];
            if (!field.isAnnotationPresent(ExcelNotation.class)) {
                continue;
            }
            ExcelNotation excelNotation = field.getAnnotation(ExcelNotation.class);
            int columnIndex = excelNotation.index() == -1 ? i : excelNotation.index();
            notationMap.put(columnIndex, excelNotation.value());
        }
        return notationMap;
    }

    /**
     * 处理单元格处理事件以应用样式和批注。
     *
     * @param context 单元格写处理器的上下文。
     */
    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {
        if (CollUtil.isEmpty(notationMap) && CollUtil.isEmpty(headColumnMap)) {
            return;
        }
        WriteCellData<?> cellData = context.getFirstCellData();
        WriteCellStyle writeCellStyle = cellData.getOrCreateStyle();

        DataFormatData dataFormatData = new DataFormatData();
        // 单元格设置为文本格式
        dataFormatData.setIndex((short) 49);
        writeCellStyle.setDataFormatData(dataFormatData);

        if (context.getHead()) {
            Cell cell = context.getCell();
            WriteSheetHolder writeSheetHolder = context.getWriteSheetHolder();
            Sheet sheet = writeSheetHolder.getSheet();
            Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
            Drawing<?> drawing = sheet.createDrawingPatriarch();
            // 设置标题字体样式
            WriteFont headWriteFont = new WriteFont();
            // 加粗
            headWriteFont.setBold(true);
            if (CollUtil.isNotEmpty(headColumnMap) && headColumnMap.containsKey(cell.getColumnIndex())) {
                // 设置字体颜色
                headWriteFont.setColor(headColumnMap.get(cell.getColumnIndex()));
            }
            writeCellStyle.setWriteFont(headWriteFont);
            CellStyle cellStyle = StyleUtil.buildCellStyle(workbook, null, writeCellStyle);
            cell.setCellStyle(cellStyle);

            if (CollUtil.isNotEmpty(notationMap) && notationMap.containsKey(cell.getColumnIndex())) {
                // 批注内容
                String notationContext = notationMap.get(cell.getColumnIndex());
                // 创建绘图对象
                Comment comment = drawing.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, (short) cell.getColumnIndex(), 0, (short) 5, 5));
                comment.setString(new XSSFRichTextString(notationContext));
                cell.setCellComment(comment);
            }
        }
    }
}