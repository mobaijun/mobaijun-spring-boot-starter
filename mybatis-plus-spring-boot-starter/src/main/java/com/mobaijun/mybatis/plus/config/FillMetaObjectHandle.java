/*
 * Copyright (C) 2022 www.mobaijun.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mobaijun.mybatis.plus.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.mobaijun.mybatis.plus.model.GlobalConstants;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: FillMetaObjectHandle
 * 类描述：填充元对象句柄
 *
 * @author MoBaiJun 2022/5/7 16:34
 */
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