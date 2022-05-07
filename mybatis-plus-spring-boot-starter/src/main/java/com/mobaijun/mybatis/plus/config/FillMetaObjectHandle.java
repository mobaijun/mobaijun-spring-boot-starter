package com.mobaijun.mybatis.plus.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.mobaijun.mybatis.plus.model.GlobalConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: FillMetaObjectHandle
 * 类描述：
 *
 * @author MoBaiJun 2022/5/7 16:34
 */
@Slf4j
public class FillMetaObjectHandle implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 逻辑删除标识
        this.strictInsertFill(metaObject, "deleted", Long.class, GlobalConstants.NOT_DELETED_FLAG);
        // 创建时间
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 修改时间
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }

}
