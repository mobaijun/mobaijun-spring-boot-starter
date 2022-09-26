package com.mobaijun.file.ftp;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.ftp.Ftp;
import cn.hutool.extra.ftp.FtpConfig;
import com.mobaijun.file.core.AbstractFileClient;
import com.mobaijun.file.exception.FileException;
import com.mobaijun.file.prop.FileProperties.FtpProperties;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: FtpFileClient
 * class description： ftp 文件客户端
 *
 * @author MoBaiJun 2022/9/26 14:48
 */
public class FtpFileClient extends AbstractFileClient {

    /**
     * ftp
     */
    private final Ftp client;

    public FtpFileClient(FtpProperties properties) {
        FtpConfig config = new FtpConfig().setHost(properties.getIp()).setPort(properties.getPort())
                .setUser(properties.getUsername()).setPassword(properties.getPassword())
                .setCharset(Charset.forName(properties.getEncoding()));

        final FtpMode mode = properties.getMode();
        if (mode == FtpMode.ACTIVE) {
            client = new Ftp(config, cn.hutool.extra.ftp.FtpMode.Active);
        } else if (mode == FtpMode.PASSIVE) {
            client = new Ftp(config, cn.hutool.extra.ftp.FtpMode.Passive);
        } else {
            client = new Ftp(config, null);
        }

        if (!StringUtils.hasText(properties.getPath())) {
            throw new NullPointerException("ftp file root path cannot be empty!");
        }

        super.rootPath = properties.getPath().endsWith(super.slash) ? properties.getPath()
                : properties.getPath() + super.slash;
    }

    /**
     * 上传文件 - 不会关闭流. 请在成功后手动关闭
     *
     * @param stream       文件流
     * @param relativePath 文件相对 getRoot() 的路径
     * @return java.lang.String 文件完整路径
     */
    @Override
    public String uploadFile(InputStream stream, String relativePath) throws IOException {
        final String path = getWholePath(relativePath);
        final String fileName = FileUtil.getName(path);
        final String dir = StrUtil.removeSuffix(path, fileName);
        // 上传失败
        if (!client.upload(dir, fileName, stream)) {
            throw new FileException(
                    String.format("File upload failed! Relative path: %s; Root path: %s; Please check whether this path exists and whether the logged-in user has permission to operate!", relativePath, path));
        }
        return path;
    }

    /**
     * 下载文件
     *
     * @param relativePath 文件相对 getRoot() 的路径
     * @return java.io.FileOutputStream 文件流
     */
    @Override
    public File downloadFile(String relativePath) {
        final String path = getWholePath(relativePath);
        final String fileName = FileUtil.getName(path);
        final String dir = StrUtil.removeSuffix(path, fileName);
        // 临时文件
        File tmpFile = FileUtil.createTempFile();
        tmpFile = FileUtil.rename(tmpFile, fileName, true);
        // 输出流
        try (FileOutputStream outputStream = new FileOutputStream(tmpFile)) {
            client.download(dir, fileName, outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tmpFile;
    }

    /**
     * 删除文件
     *
     * @param relativePath 文件相对 getRoot() 的路径
     * @return boolean
     */
    @Override
    public boolean deleteFile(String relativePath) {
        return client.delFile(getWholePath(relativePath));
    }
}
