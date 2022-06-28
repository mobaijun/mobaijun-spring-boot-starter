package com.mobaijun.table.constant;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: TableConstant
 * class description： table sql
 *
 * @author MoBaiJun 2022/6/28 16:32
 */
public class TableConstant {

    /**
     * MySQL 备份表
     */
    public final static String MYSQL_BACKUPS = "mysql_backups` (\n" +
            "  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',\n" +
            "  `mysql_ip` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据库IP',\n" +
            "  `mysql_port` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据库端口',\n" +
            "  `mysql_cmd` varchar(230) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备份命令',\n" +
            "  `mysql_back_cmd` varchar(230) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '恢复命令',\n" +
            "  `database_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据库名称',\n" +
            "  `backups_path` varchar(180) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备份数据地址',\n" +
            "  `backups_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备份文件名称',\n" +
            "  `operation` int(0) NULL DEFAULT NULL COMMENT '操作次数',\n" +
            "  `status` int(0) NULL DEFAULT NULL COMMENT '数据状态（0正常，1删除）',\n" +
            "  `recovery_time` datetime(0) NULL DEFAULT NULL COMMENT '恢复时间',\n" +
            "  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '备份时间',\n" +
            "  PRIMARY KEY (`id`) USING BTREE,\n" +
            "  INDEX `baskups_index`(`mysql_ip`, `mysql_port`, `backups_path`, `database_name`, `backups_name`) USING BTREE COMMENT '索引'\n" +
            ") ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'MySQL数据备份表' ROW_FORMAT = COMPACT;";

    /**
     * 字典数据表
     */
    public final static String DICT_DATA = "dict_data` (\n" +
            "  `dict_code` bigint(0) NOT NULL COMMENT '字典编码',\n" +
            "  `dict_sort` tinyint(1) NULL DEFAULT NULL COMMENT '字典排序',\n" +
            "  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典标签',\n" +
            "  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典键值',\n" +
            "  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典类型',\n" +
            "  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',\n" +
            "  `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '表格回显样式',\n" +
            "  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否默认（Y是，N否）',\n" +
            "  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字段备注',\n" +
            "  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '状态（0：正常，1：删除）',\n" +
            "  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者',\n" +
            "  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者',\n" +
            "  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',\n" +
            "  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',\n" +
            "  PRIMARY KEY (`dict_code`) USING BTREE\n" +
            ") ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典数据表' ROW_FORMAT = DYNAMIC;";

    /**
     * 字典类型表
     */
    public final static String DICT_TYPE = "dict_type` (\n" +
            "  `dict_id` bigint(0) NOT NULL COMMENT '字典主键',\n" +
            "  `dict_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典名称',\n" +
            "  `dict_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典类型',\n" +
            "  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典说明',\n" +
            "  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '状态（0：正常，1：删除）',\n" +
            "  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',\n" +
            "  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新人',\n" +
            "  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',\n" +
            "  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',\n" +
            "  PRIMARY KEY (`dict_id`) USING BTREE\n" +
            ") ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典类型表' ROW_FORMAT = DYNAMIC;";

    /**
     * 组件配置表
     */
    public final static String SECOND = "second` (\n" +
            "  `id` bigint(0) NOT NULL COMMENT '主键',\n" +
            "  `second_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '组件名称',\n" +
            "  `second_img` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '组件图标',\n" +
            "  `second_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '组件链接',\n" +
            "  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '状态（0：正常，1：删除）',\n" +
            "  PRIMARY KEY (`id`) USING BTREE\n" +
            ") ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '组件配置' ROW_FORMAT = DYNAMIC;";

    /**
     * 参数配置表
     */
    public final static String CONFIG = "config` (\n" +
            "  `config_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '参数主键',\n" +
            "  `config_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数名称',\n" +
            "  `config_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数键名',\n" +
            "  `config_value` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数键值',\n" +
            "  `config_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',\n" +
            "  `deleted` bigint(0) NULL DEFAULT 0 COMMENT '逻辑删除字段（0正常，1删除）',\n" +
            "  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',\n" +
            "  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',\n" +
            "  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',\n" +
            "  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',\n" +
            "  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',\n" +
            "  PRIMARY KEY (`config_id`) USING BTREE\n" +
            ") ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '参数配置表' ROW_FORMAT = COMPACT;";

    /**
     * 全国地区表
     */
    public final static String REGION = "region` (\n" +
            "  `region_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '地区主键编号',\n" +
            "  `region_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '地区名称',\n" +
            "  `region_short_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地区缩写',\n" +
            "  `region_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '行政地区编号',\n" +
            "  `region_parent_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地区父id',\n" +
            "  `region_level` int(0) NULL DEFAULT NULL COMMENT '地区级别 1-省、自治区、直辖市 2-地级市、地区、自治州、盟 3-市辖区、县级市、县',\n" +
            "  PRIMARY KEY (`region_id`) USING BTREE\n" +
            ") ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '地区表' ROW_FORMAT = DYNAMIC;";
}
