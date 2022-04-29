package com.mobaijun.influxdb.core.model;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: DeleteModel
 * 类描述： 删除
 *
 * @author MoBaiJun 2022/4/29 13:55
 */
public class DeleteModel extends BaseModel {

    public DeleteModel() {
    }

    /**
     * 表名
     *
     * @param measurement 表名
     */
    public DeleteModel(String measurement) {
        super(measurement);
    }
}
