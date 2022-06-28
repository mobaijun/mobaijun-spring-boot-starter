package com.mobaijun.table.entity.second;

import com.mobaijun.table.base.BaseCreateTable;
import com.mobaijun.table.constant.Constatnt;
import com.mobaijun.table.constant.TableConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: SecondScript
 * class description： 组件配置表
 *
 * @author MoBaiJun 2022/6/28 14:16
 */
public class SecondScript extends BaseCreateTable {
    /**
     * logger
     */
    private static final Logger log = LoggerFactory.getLogger(SecondScript.class);

    @Override
    public String splicingSql(String tablePrefix) {
        StringBuilder sql = new StringBuilder();
        // 项目配置表
        if (StringUtils.hasText(tablePrefix)) {
            // 拼接表前缀
            sql
                    .append(Constatnt.TABLE_PREFIX)
                    .append(tablePrefix)
                    .append(TableConstant.SECOND);
        } else {
            // 空的不拼接
            sql
                    .append(Constatnt.TABLE_PREFIX)
                    .append(TableConstant.SECOND);
        }
        log.info("The SQL statement to create the configuration component configuration table is:{}", sql);
        return sql.toString();
    }
}
