/*
 * Copyright (C) 2022 [www.mobaijun.com]
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
package com.mobaijun.base.model;

import java.util.List;

/**
 * Description: [树节点]
 * Author: [mobaijun]
 * Date: [2024/6/25 16:58]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public interface TreeNode<I> {

    /**
     * 获取节点 key
     *
     * @return 树节点 key
     */
    I getKey();

    /**
     * 获取该节点的父节点 key
     *
     * @return 父节点 key
     */
    I getParentKey();

    /**
     * 获取所有子节点
     *
     * @return 子节点列表
     */
    <T extends TreeNode<I>> List<T> getChildren();

    /**
     * 设置节点的子节点列表
     *
     * @param children 子节点
     */
    <T extends TreeNode<I>> void setChildren(List<T> children);
}
