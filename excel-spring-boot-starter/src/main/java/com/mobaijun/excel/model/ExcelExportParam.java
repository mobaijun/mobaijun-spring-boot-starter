package com.mobaijun.excel.model;

import com.alibaba.excel.support.ExcelTypeEnum;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: ExcelExportParam
 * class description：Excel 导出参数
 *
 * @author MoBaiJun 2022/10/28 13:36
 */
public class ExcelExportParam<T> {

    /**
     * 需要导出的数据列表
     */
    List<T> dataList;

    /**
     * Excel每行数据转换成的对象类
     */
    Class<T> clazz;

    /**
     * 工作簿名称 导出/写入文件用
     */
    String sheetName;

    /**
     * 下载提示的文件名 无需带上xls、xlsx后缀
     */
    String fileName;

    /**
     * Excel类型 xls、xlsx
     */
    ExcelTypeEnum excelTypeEnum;

    /**
     * http 响应
     */
    HttpServletResponse response;

    /**
     * 文件写入绝对路径 写入到服务器磁盘用
     */
    String excelFileWriteAbsolutePath;

    @Override
    public String toString() {
        return "ExcelExportParam{" +
                "dataList=" + dataList +
                ", clazz=" + clazz +
                ", sheetName='" + sheetName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", excelTypeEnum=" + excelTypeEnum +
                ", response=" + response +
                ", excelFileWriteAbsolutePath='" + excelFileWriteAbsolutePath + '\'' +
                '}';
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ExcelTypeEnum getExcelTypeEnum() {
        return excelTypeEnum;
    }

    public void setExcelTypeEnum(ExcelTypeEnum excelTypeEnum) {
        this.excelTypeEnum = excelTypeEnum;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public String getExcelFileWriteAbsolutePath() {
        return excelFileWriteAbsolutePath;
    }

    public void setExcelFileWriteAbsolutePath(String excelFileWriteAbsolutePath) {
        this.excelFileWriteAbsolutePath = excelFileWriteAbsolutePath;
    }
}
