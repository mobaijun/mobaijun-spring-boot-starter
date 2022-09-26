package com.mobaijun.file.core;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: AbstractFileClient
 * class description：
 *
 * @author MoBaiJun 2022/9/26 14:51
 */
public abstract class AbstractFileClient implements FileClient {

    /**
     * 分割符
     */
    protected String slash = "/";

    /**
     * 根目录
     */
    protected String rootPath;

    /**
     * 获取操作的根路径
     */
    public String getRoot() {
        return rootPath;
    }

    /**
     * 获取完整路径
     *
     * @param relativePath 文件相对 getRoot() 的路径
     */
    public String getWholePath(String relativePath) {
        if (relativePath.startsWith(slash)) {
            return getRoot() + relativePath.substring(1);
        }
        return getRoot() + relativePath;
    }
}
