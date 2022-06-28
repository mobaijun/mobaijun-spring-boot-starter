package com.mobaijun.table.entity.dict;

import com.mobaijun.table.base.BaseCreateTable;
import com.mobaijun.table.constant.Constatnt;
import com.mobaijun.table.constant.TableConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: DictAllScript
 * class description： 字典数据表和字典类型表
 *
 * @author MoBaiJun 2022/6/28 14:14
 */
public class DictAllScript extends BaseCreateTable {

    /**
     * logger
     */
    private static final Logger log = LoggerFactory.getLogger(DictAllScript.class);

    @Override
    public String splicingSql(String tablePrefix) {
        StringBuilder sql = new StringBuilder();
        // 项目配置表
        if (StringUtils.hasText(tablePrefix)) {
            // 拼接表前缀
            sql
                    // 字典数据表
                    .append(Constatnt.TABLE_PREFIX)
                    .append(tablePrefix)
                    .append(TableConstant.DICT_DATA)
                    // 字典类型表
                    .append(Constatnt.TABLE_PREFIX)
                    .append(tablePrefix)
                    .append(TableConstant.DICT_TYPE);
        } else {
            // 空的不拼接
            sql
                    // 字典数据表
                    .append(Constatnt.TABLE_PREFIX)
                    .append(TableConstant.DICT_DATA)
                    // 字典类型表
                    .append(Constatnt.TABLE_PREFIX)
                    .append(TableConstant.DICT_TYPE);
        }
        log.info("The SQL statement to create the dictionary configuration table is:{}", sql);
        return sql.toString();
    }
}
