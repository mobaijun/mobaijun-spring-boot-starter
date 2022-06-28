package com.mobaijun.table.prop;

import com.mobaijun.table.constant.TableTypeEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.List;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: TableProperties
 * class description： 读取 spring 数据源配置
 *
 * @author MoBaiJun 2022/6/28 14:11
 */
@ConfigurationProperties(TableProperties.PREFIX)
@EnableConfigurationProperties(TableProperties.class)
public class TableProperties {
    public static final String PREFIX = "spring.datasource";

    /**
     * Fully qualified name of the JDBC driver. Auto-detected based on the URL by default.
     */
    private String driverClassName;

    /**
     * JDBC URL of the database.
     */
    private String url;

    /**
     * Login username of the database.
     */
    private String username;

    /**
     * Login password of the database.
     */
    private String password;

    /**
     * table config
     */
    private TableConfig tableConfig;

    /**
     * table config
     */
    public static class TableConfig {
        /**
         * Table prefix to be created
         */
        private String tablePrefix;

        /**
         * Type of table to be created
         */
        private List<TableTypeEnum> typeEnum;

        public String getTablePrefix() {
            return tablePrefix;
        }

        public void setTablePrefix(String tablePrefix) {
            this.tablePrefix = tablePrefix;
        }

        public List<TableTypeEnum> getTypeEnum() {
            return typeEnum;
        }

        public void setTypeEnum(List<TableTypeEnum> typeEnum) {
            this.typeEnum = typeEnum;
        }
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TableConfig getTableConfig() {
        return tableConfig;
    }

    public void setTableConfig(TableConfig tableConfig) {
        this.tableConfig = tableConfig;
    }
}
