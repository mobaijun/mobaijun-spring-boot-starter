package com.mobaijun.mybatis.plus.util;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.mobaijun.core.spring.SpringUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Description: [ID 生成工具类]
 * <p>
 * 基于 MyBatis-Plus 的 {@link IdentifierGenerator} 提供多样化的 ID 生成策略，
 * 支持雪花算法 ID、UUID 以及自定义前缀 ID。
 * </p>
 * Author: [mobaijun]
 * Date: [2026/01/06]
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IdGeneratorUtil {

    /**
     * 持有 MyBatis-Plus 的标识符生成器实例
     * <p>通过 SpringUtils 动态获取，确保与系统配置的生成策略一致</p>
     */
    private static final IdentifierGenerator GENERATOR = SpringUtil.getBean(IdentifierGenerator.class);

    /**
     * 生成字符串类型主键 ID
     * <p>
     * 默认采用雪花算法（Snowflake），返回 String 格式，防止前端长整型（Long）精度丢失。
     * </p>
     *
     * @return 字符串格式主键 ID
     */
    public static String nextId() {
        return GENERATOR.nextId(null).toString();
    }

    /**
     * 生成 Long 类型主键 ID
     * <p>
     * 自动将生成的数字型主键转换为 Long 类型。
     * </p>
     *
     * @return Long 类型主键 ID
     */
    public static Long nextLongId() {
        return GENERATOR.nextId(null).longValue();
    }

    /**
     * 生成 Number 类型主键 ID
     * <p>
     * 推荐在需要保留原始数据类型（如 BigInteger 或 Long）时使用。
     * </p>
     *
     * @return Number 类型主键 ID
     */
    public static Number nextNumberId() {
        return GENERATOR.nextId(null);
    }

    /**
     * 根据实体生成数字型主键 ID
     * <p>
     * 若自定义的生成器逻辑依赖于实体内容（如根据租户 ID 生成），则使用此方法。
     * </p>
     *
     * @param entity 实体对象
     * @return Number 类型主键 ID
     */
    public static Number nextId(Object entity) {
        return GENERATOR.nextId(entity);
    }

    /**
     * 根据实体生成字符串主键 ID
     *
     * @param entity 实体对象
     * @return 字符串格式主键 ID
     */
    public static String nextStringId(Object entity) {
        return GENERATOR.nextId(entity).toString();
    }

    /**
     * 生成 32 位 UUID（不带连字符）
     * <p>
     * 底层调用 {@link IdWorker#get32UUID()}。
     * </p>
     *
     * @return 32 位 UUID 字符串
     */
    public static String nextUUID() {
        return IdWorker.get32UUID();
    }

    /**
     * 根据实体生成 32 位 UUID
     * <p>
     * 默认实现通常忽略实体，但保留该接口以支持基于实体的自定义 UUID 逻辑。
     * </p>
     *
     * @param entity 实体对象
     * @return 32 位 UUID 字符串
     */
    public static String nextUUID(Object entity) {
        return GENERATOR.nextUUID(entity);
    }

    /**
     * 生成带指定前缀的雪花 ID
     * <p>
     * 适用场景：业务订单号区分，如：{@code ORD + 18764532...}
     * </p>
     *
     * @param prefix 自定义前缀
     * @return 带前缀的 ID 字符串
     */
    public static String nextIdWithPrefix(String prefix) {
        return prefix + nextId();
    }

    /**
     * 生成带指定前缀的 UUID
     *
     * @param prefix 自定义前缀
     * @return 带前缀的 UUID 字符串
     */
    public static String nextUUIDWithPrefix(String prefix) {
        return prefix + nextUUID();
    }
}