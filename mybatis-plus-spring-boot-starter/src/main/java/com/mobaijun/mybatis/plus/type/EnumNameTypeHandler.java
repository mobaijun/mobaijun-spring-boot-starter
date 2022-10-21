package com.mobaijun.mybatis.plus.type;

import com.baomidou.mybatisplus.annotation.IEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: EnumNameTypeHandler
 * 类描述：普通枚举类型处理. 根据 name() 返回值判断枚举值
 *
 * @author MoBaiJun 2022/5/7 16:11
 */
public class EnumNameTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

    private final Class<E> type;

    public EnumNameTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null) {
            ps.setString(i, "");
        } else {
            Object val = getValByEnum(parameter);
            if (jdbcType == null) {
                ps.setString(i, val == null ? null : val.toString());
            } else {
                ps.setObject(i, val, jdbcType.TYPE_CODE);
            }
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getEnumByName(rs.getString(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getEnumByName(rs.getString(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getEnumByName(cs.getString(columnIndex));
    }

    boolean isIEnum(E e) {
        return IEnum.class.isAssignableFrom(e.getClass());
    }

    Object getValByEnum(E e) {
        // IEnum
        if (isIEnum(e)) {
            return ((IEnum<?>) e).getValue();
        }
        return e.name();
    }

    /**
     * 根据枚举 name() 获取枚举
     */
    E getEnumByName(String val) {
        for (E e : type.getEnumConstants()) {
            Object ev = getValByEnum(e);
            if (ev == null) {
                if (val == null) {
                    return e;
                }
                continue;
            }

            if (val.equals(ev.toString())) {
                return e;
            }
        }
        return null;
    }
}
