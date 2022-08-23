package com.mobaijun.influxdb.core;

import cn.hutool.log.Log;
import com.mobaijun.influxdb.core.constant.Constant;
import com.mobaijun.influxdb.util.CommonUtils;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: Insert
 * 类描述： 新增
 *
 * @author MoBaiJun 2022/4/29 14:03
 */
public class Insert extends BaseQuery {

    /**
     * tools log
     */
    private static final Log log = Log.get(Insert.class);

    /**
     * 构造条件
     *
     * @param object Object
     * @return String
     */
    public static String build(Object object) {
        Objects.requireNonNull(object, "实体不能为空");
        StringBuilder insert = new StringBuilder();
        String time = "";
        Class<?> clazz = object.getClass();
        Measurement measurement = clazz.getAnnotation(Measurement.class);
        insert.append(measurement.name());
        Field[] fields = clazz.getDeclaredFields();
        int i = 0;
        for (Field field : fields) {
            try {
                Column column = field.getAnnotation(Column.class);
                field.setAccessible(true);
                if (column.tag()) {
                    if (field.get(object) != null) {
                        insert.append(Constant.COMMA).append(column.name()).append(Constant.EQUAL_SIGN).append(field.get(object));
                    }
                } else {
                    if (field.get(object) != null) {
                        if (Constant.TIME.equals(column.name())) {
                            time = CommonUtils.parseLocalDateTimeToInstant((LocalDateTime) field.get(object)).getEpochSecond() + "000000000";
                        } else {
                            if (i == 0) {
                                insert.append(Constant.SPACE);
                            } else {
                                insert.append(Constant.COMMA);
                            }
                            insert.append(column.name()).append(Constant.EQUAL_SIGN).append(field.get(object));
                            i++;
                        }

                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                log.error("Influxdb save error. error :{}", e.getMessage());
            }
        }
        insert.append(Constant.SPACE).append(time);
        String sql = insert.toString();
        log.warn("The new sentence is :{}" + sql);
        return sql;
    }
}
