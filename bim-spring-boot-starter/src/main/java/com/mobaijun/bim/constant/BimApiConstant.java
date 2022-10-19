package com.mobaijun.bim.constant;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: BimApiConstant
 * class description：
 *
 * @author MoBaiJun 2022/10/19 8:41
 */
public class BimApiConstant {

    /**
     * <a href="https://file.bimface.com/upload/policy">获取文件直传的policy凭证</a>
     */
    public static final String GET_POLICY = "https://file.bimface.com/upload/policy";

    /**
     * <a href="https://bf-prod-srcfile.oss-cn-beijing.aliyuncs.combim">上传文件地址</a>
     */
    public static final String BIM_UPLOAD = "https://bf-prod-srcfile.oss-cn-beijing.aliyuncs.com";

    /**
     * <a href="https://file.bimface.com/appendFiles">断点续传</a>
     */
    public static final String APPEND_FILES = "https://bf-prod-srcfile.oss-cn-beijing.aliyuncs.com";

    /**
     * <a href="https://api.bimface.com/translate">BIM 模板转化</a>
     */
    public static final String BIM_TRANSLATE = "https://api.bimface.com/translate";


    /**
     * <a href="https://api.bimface.com/view/token">hu获取文件展示 view token URL</a>
     */
    public static final String VIEW_TOKEN_URL = "https://api.bimface.com/view/token";

    /**
     * <a href="https://bimface.com/api/console/preview">用来展示视图url</a>?
     */
    public static final String PREVIEW = "https://bimface.com/api/console/preview?";

    /**
     * 获取文件下载链接
     */
    public static final String DOWNLOAD = "https://file.bimface.com/download/url";

    /**
     * <a href="https://file.bimface.com/file">bim源文件删除</a>
     */
    public static final String DELETE_BIM_FILE = "https://file.bimface.com/file";

    /**
     * <a href="https://api.bimface.com/files/{fileId}/split">发起图纸拆分</a>
     */
    public static final String SPLIT = "https://api.bimface.com/files/%s/split";

    /**
     * <a href="https://file.bimface.com/upload">文件上传</a>
     */
    public static final String BIM_UPLOAD_FILE = "https://file.bimface.com/upload?name=";

    /**
     * bim 文件后缀
     */
    public static final String SUFFIX = ".rvt";

    /**
     * <a href="https://file.bimface.com/upload/policy">根据policy凭证在web端上传文件</a>
     */
    public static final String UPLOAD_POLICY = "https://file.bimface.com/upload/policy";

    /**
     * <a href="https://file.bimface.com/files/">获取文件信息</a>
     */
    public static final String FILE_INFO = "https://file.bimface.com/files/";
}
