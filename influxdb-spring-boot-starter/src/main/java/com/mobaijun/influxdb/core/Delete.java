package com.mobaijun.influxdb.core;

import com.mobaijun.influxdb.core.constant.Constant;
import com.mobaijun.influxdb.core.model.DeleteModel;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: Delete
 * 类描述： 删除
 *
 * @author MoBaiJun 2022/4/29 14:04
 */
public class Delete extends BaseQuery {
    /**
     * 构造条件
     * <p>
     * 注意 where 条件中 map参数仅能是 tag
     * 这是由 influxdb 本身决定的
     *
     * @param model
     * @return
     */
    public static String build(DeleteModel model) {
        Objects.requireNonNull(model.getMeasurement(), Constant.DELETE_MEASUREMENT);
        StringBuilder delete = new StringBuilder();
        delete.append(Constant.DELETE_DROM).append(model.getMeasurement());
        if (!ObjectUtils.isEmpty(model.getWhere())) {
            delete.append(Constant.WHERE).append(model.getWhere());
        } else {
            throw new RuntimeException("where 条件缺失");
        }
        String sql = delete.toString();
        log.info(sql);
        return sql;
    }
}
