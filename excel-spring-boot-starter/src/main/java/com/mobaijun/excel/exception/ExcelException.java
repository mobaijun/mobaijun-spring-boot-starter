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
