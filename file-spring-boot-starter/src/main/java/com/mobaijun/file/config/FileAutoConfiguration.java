package com.mobaijun.file.config;

import com.mobaijun.file.core.FileClient;
import com.mobaijun.file.ftp.FtpFileClient;
import com.mobaijun.file.local.LocalFileClient;
import com.mobaijun.file.prop.FileProperties;
import com.mobaijun.file.prop.FileProperties.LocalProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: FileAutoConfiguration
 * class description： 文件自动配置类
 *
 * @author MoBaiJun 2022/9/26 14:46
 */
@AutoConfiguration
public class FileAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(FileClient.class)
    @ConditionalOnProperty(prefix = FileProperties.PREFIX_FTP, name = "ip")
    public FileClient ballcatFileFtpClient(FileProperties properties) {
        return new FtpFileClient(properties.getFtp());
    }

    @Bean
    @ConditionalOnMissingBean(FileClient.class)
    public FileClient ballcatFileLocalClient(FileProperties properties) throws IOException {
        LocalProperties localProperties = properties == null || properties.getLocal() == null ? new LocalProperties()
                : properties.getLocal();
        return new LocalFileClient(localProperties);
    }
}
