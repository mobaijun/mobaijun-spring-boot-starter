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
    void easyExportDownload(ExcelExportParam excelExportParam);

    /**
     * 简单的写入Excel文件到指定路径
     *
     * @param excelExportParam Excel导出参数
     */
    void easyWriteToFile(ExcelExportParam excelExportParam);

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
