package com.mobaijun.mybatis.plus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mobaijun.mybatis.plus.model.domain.PageParam;
import com.mobaijun.mybatis.plus.toolkit.PageUtil;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * InterfaceName: ExtendMapper
 * 接口描述：
 *
 * @author MoBaiJun 2022/5/7 16:23
 */
public interface ExtendMapper<T> extends BaseMapper<T> {

    /**
     * 根据 PageParam 生成一个 IPage 实例
     *
     * @param pageParam 分页参数
     * @param <V>       返回的 Record 对象
     * @return IPage
     */
    default <V> IPage<V> prodPage(PageParam pageParam) {
        return PageUtil.prodPage(pageParam);
    }

    /**
     * 批量插入数据 实现类 InsertBatchSomeColumn
     *
     * @param list 数据列表
     * @return int 改动行
     */
    int insertBatchSomeColumn(@Param("collection") Collection<T> list);
}
