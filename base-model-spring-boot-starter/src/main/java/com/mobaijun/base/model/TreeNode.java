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
