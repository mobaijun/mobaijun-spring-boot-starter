package com.mobaijun.table.entity.defaults;

import com.mobaijun.table.base.BaseCreateTable;
import com.mobaijun.table.constant.Constatnt;
import com.mobaijun.table.constant.TableConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: DefaultAll
 * class description： 默认全部数据表
 *
 * @author MoBaiJun 2022/6/28 16:09
 */
public class DefaultAll extends BaseCreateTable {

    /**
     * logger
     */
    private static final Logger log = LoggerFactory.getLogger(DefaultAll.class);

    @Override
    public String splicingSql(String tablePrefix) {
        StringBuilder sql = new StringBuilder();
        // 项目配置表
        if (StringUtils.hasText(tablePrefix)) {
            // 拼接表前缀
            sql
                    // MySQL备份表
                    .append(Constatnt.TABLE_PREFIX)
                    .append(tablePrefix)
                    .append(TableConstant.MYSQL_BACKUPS)
                    // 项目配置表
                    .append(Constatnt.TABLE_PREFIX)
                    .append(tablePrefix)
                    .append(TableConstant.CONFIG)
                    // 字典表
                    .append(Constatnt.TABLE_PREFIX)
                    .append(tablePrefix)
                    .append(TableConstant.DICT_TYPE)
                    .append(Constatnt.TABLE_PREFIX)
                    .append(tablePrefix)
                    .append(TableConstant.DICT_DATA)
                    // 地区表
                    .append(Constatnt.TABLE_PREFIX)
                    .append(tablePrefix)
                    .append(TableConstant.REGION)
                    // 组件配置表
                    .append(Constatnt.TABLE_PREFIX)
                    .append(tablePrefix)
                    .append(TableConstant.SECOND);
        } else {
            // 空的不拼接
            sql
                    // MySQL备份表
                    .append(Constatnt.TABLE_PREFIX)
                    .append(TableConstant.MYSQL_BACKUPS)
                    // 项目配置表
                    .append(Constatnt.TABLE_PREFIX)
                    .append(TableConstant.CONFIG)
                    // 字典表
                    .append(Constatnt.TABLE_PREFIX)
                    .append(TableConstant.DICT_TYPE)
                    .append(Constatnt.TABLE_PREFIX)
                    .append(TableConstant.DICT_DATA)
                    // 地区表
                    .append(Constatnt.TABLE_PREFIX)
                    .append(TableConstant.REGION)
                    // 组件配置表
                    .append(Constatnt.TABLE_PREFIX)
                    .append(TableConstant.SECOND);
        }
        log.info("The SQL statement to create all configuration tables is:{}", sql);
        return sql.toString();
    }
}
