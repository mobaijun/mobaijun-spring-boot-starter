package com.mobaijun.mybatis.plus.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Getter;
import lombok.Setter;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: LogicDeletedBaseEntity
 * 类描述：逻辑删除的实体类基类
 *
 * @author MoBaiJun 2022/5/7 16:03
 */
@Getter
@Setter
public abstract class LogicDeletedBaseEntity extends BaseEntity {

    /**
     * 逻辑删除标识，已删除: 删除时间戳，未删除: 0
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Long deleted;
}
