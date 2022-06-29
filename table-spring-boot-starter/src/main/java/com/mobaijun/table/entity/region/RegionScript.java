package com.mobaijun.table.entity.region;

import com.mobaijun.table.base.BaseCreateTable;
import com.mobaijun.table.constant.Constatnt;
import com.mobaijun.table.constant.TableConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: RegionScript
 * class description： 地区信息表
 * 如果需要初始化数据，可以到本仓库提取<<a href="https://github.com/april-projects/init-data/">...</a>>
 *
 * @author MoBaiJun 2022/6/28 14:17
 */
public class RegionScript extends BaseCreateTable {

    /**
     * logger
     */
    private static final Logger log = LoggerFactory.getLogger(RegionScript.class);

    @Override
    public String splicingSql(String tablePrefix) {
        StringBuilder sql = new StringBuilder();
        // 项目配置表
        if (StringUtils.hasText(tablePrefix)) {
            // 拼接表前缀
            sql
                    .append(Constatnt.TABLE_PREFIX)
                    .append(tablePrefix)
                    .append(TableConstant.REGION);
        } else {
            // 空的不拼接前缀
            sql
                    .append(Constatnt.TABLE_PREFIX)
                    .append(TableConstant.REGION);
        }
        log.info("The SQL statement to create the region information configuration table is:{}", sql);
        return sql.toString();
    }
}
