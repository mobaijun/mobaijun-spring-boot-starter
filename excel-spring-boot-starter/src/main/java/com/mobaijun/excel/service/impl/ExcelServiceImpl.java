package com.mobaijun.excel.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.mobaijun.excel.constant.ExcelConstants;
import com.mobaijun.excel.exception.ExcelException;
import com.mobaijun.excel.listener.SimpleDataListener;
import com.mobaijun.excel.model.ExcelExportParam;
import com.mobaijun.excel.service.ExcelService;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: ExcelServiceImpl
 * class description：
 *
 * @author MoBaiJun 2022/10/28 13:40
 */
public class ExcelServiceImpl implements ExcelService {

    @Override
    public <T> void easyExportDownload(ExcelExportParam<T> excelExportParam) {
        if (excelExportParam == null) {
            return;
        }

        try {
            HttpServletResponse response = excelExportParam.getResponse();
            if (response == null) {
                throw new NullPointerException("HttpServletResponse information is abnormal! Please try again later!");
            }

            if (excelExportParam.getClazz() == null) {
                throw new NullPointerException("excelExportParam attempt exception! Please confirm the parameters and try again!");
            }

            // 默认值
            createDefaultValue(excelExportParam);

            ExcelTypeEnum excelTypeEnum = excelExportParam.getExcelTypeEnum();

            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            String fileName = URLEncoder.encode(excelExportParam.getFileName(), StandardCharsets.UTF_8.name()).replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", String.format("%s%s%s", "attachment;filename*=utf-8''", fileName, excelTypeEnum.getValue()));

            EasyExcel.write(response.getOutputStream(), excelExportParam
                            .getClazz())
                    .excelType(excelTypeEnum)
                    .sheet(excelExportParam.getSheetName())
                    .doWrite(excelExportParam.getDataList());

        } catch (Exception e) {
            // 组装提示信息
            throw new ExcelException(ExcelConstants.OFFICE_MODULE_NAME, e);
        }

    }

    @Override
    public <T> void easyWriteToFile(ExcelExportParam<T> excelExportParam) {
        // 默认值
        createDefaultValue(excelExportParam);

        ExcelTypeEnum excelTypeEnum = excelExportParam.getExcelTypeEnum();
        String excelFileWriteAbsolutePath = excelExportParam.getExcelFileWriteAbsolutePath();

        try {
            EasyExcel.write(excelFileWriteAbsolutePath, excelExportParam.getClazz()).excelType(excelTypeEnum).sheet(excelExportParam.getSheetName()).doWrite(excelExportParam.getDataList());
        } catch (Exception e) {
            // 组装提示信息
            throw new ExcelException(ExcelConstants.OFFICE_MODULE_NAME, e);
        }
    }

    @Override
    public <T> List<T> easyReadToList(InputStream inputStream, Class<T> clazz) {
        if (inputStream == null) {
            return Collections.emptyList();
        }

        // 创建一个简单的数据监听器
        SimpleDataListener<T> readListener = new SimpleDataListener<>();

        // 读取文件
        try {
            EasyExcel.read(inputStream, clazz, readListener).sheet().doRead();
        } catch (Exception e) {
            // 组装提示信息
            throw new ExcelException(ExcelConstants.OFFICE_MODULE_NAME, e);
        }

        return readListener.getDataList();
    }

    @Override
    public <T> List<T> easyReadToList(InputStream inputStream, Integer rowNum, Class<T> clazz) {
        if (inputStream == null) {
            return Collections.emptyList();
        }

        // 创建一个简单的数据监听器
        SimpleDataListener<T> readListener = new SimpleDataListener<>();

        // 读取文件
        try {
            EasyExcel.read(inputStream, clazz, readListener).sheet().headRowNumber(rowNum).doRead();
        } catch (Exception e) {
            // 组装提示信息
            throw new ExcelException(ExcelConstants.OFFICE_MODULE_NAME, e);
        }
        return readListener.getDataList();
    }

    /**
     * excel导出文件的默认属性
     *
     * @param param Excel导出参数
     */
    private <T> void createDefaultValue(ExcelExportParam<T> param) {
        if (param.getSheetName().isEmpty()) {
            param.setSheetName(ExcelConstants.OFFICE_EXCEL_DEFAULT_SHEET_NAME);
        }

        if (param.getFileName().isEmpty()) {
            param.setFileName(ExcelConstants.OFFICE_EXCEL_EXPORT_DEFAULT_FILE_NAME);
        }

        if (param.getExcelTypeEnum() == null) {
            param.setExcelTypeEnum(ExcelTypeEnum.XLSX);
        }
    }
}
